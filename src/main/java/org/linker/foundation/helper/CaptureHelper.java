package org.linker.foundation.helper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.linker.foundation.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author RWM
 * @date 2018/7/30
 */
public final class CaptureHelper {
    private static final Logger LOG = LoggerFactory.getLogger(CaptureHelper.class);
    private static final ExecutorService CAPTURE_ES = ThreadFactoryHelper.newCachedThreadPool(64, "CAPTURE_ES");

    private CaptureHelper() {
    }


    /**
     * 异步单个操作，无返回值
     */
    public static <T> void async(Runner runner, Consumer<ResultSet<T>> consumer) {
        CAPTURE_ES.submit(() -> {
            Throwable cause = null;
            try {
                if (null != runner) {
                    runner.run();
                }
            } catch (Exception e) {
                cause = e;
            }
            consumer(consumer, cause, null);
        });
    }

    /**
     * 异步单个操作，有返回值
     */
    public static <T> void async(Supplier<T> supplier, Consumer<ResultSet<T>> consumer) {
        CAPTURE_ES.submit(() -> {
            Throwable cause = null;
            T t = null;
            try {
                if (null != supplier) {
                    t = supplier.get();
                }
            } catch (Exception e) {
                cause = e;
            }
            consumer(consumer, cause, t);
        });
    }

    /**
     * 异步多个操作，默认超时时间5秒
     */
    public static List<Object> submit(List<Supplier> supplierList) {
        List<Object> resultList = Lists.newArrayList();
        if (!CollectionUtils.isNullOrEmpty(supplierList)) {
            List<Future<?>> futureList = Lists.newArrayList();
            try {
                supplierList.forEach(supplier -> futureList.add(CAPTURE_ES.submit(() -> supplier.get())));
            } catch (Exception e) {
                resultList.clear();
                throw new RuntimeException("capture submit error: ", e);
            } finally {
                for (Future<?> future : futureList) {
                    if (null != future) {
                        future.cancel(true);
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 异步多个操作，对每个操作设置一个超时时间，单位：秒
     */
    public static List<Object> submit(LinkedHashMap<Supplier, Long> supplierMap) {
        List<Object> resultList = Lists.newArrayList();
        if (!CollectionUtils.isNullOrEmpty(supplierMap)) {
            LinkedHashMap<Future<?>, Long> futureMap = Maps.newLinkedHashMap();
            try {
                supplierMap.forEach((s, t) -> futureMap.put(CAPTURE_ES.submit(() -> s.get()), t));
                for (Map.Entry<Future<?>, Long> entry : futureMap.entrySet()) {
                    resultList.add(entry.getKey().get(entry.getValue(), TimeUnit.SECONDS));
                }
            } catch (Exception e) {
                resultList.clear();
                throw new RuntimeException("capture submit error: ", e);
            } finally {
                for (Map.Entry<Future<?>, Long> entry : futureMap.entrySet()) {
                    if (null != entry.getKey()) {
                        entry.getKey().cancel(true);
                    }
                }
            }
        }
        return resultList;
    }

    private static <T> void consumer(Consumer<ResultSet<T>> consumer, Throwable cause, T t) {
        if (null == consumer) {
            LOG.debug("async process ignore consumer");
            if (null != cause) {
                LOG.error("async process error {}", cause);
            }
        } else {
            try {
                consumer.accept(ResultSet.newInstance(null == cause, t, cause));
            } catch (Exception e) {
                LOG.error("async process do consumer error {}", e);
            }
        }
    }

    public interface Runner {
        void run();
    }

    public static class ResultSet<T> {
        public boolean result;
        public T data;
        public Throwable cause;

        private ResultSet(boolean result, T data, Throwable cause) {
            this.result = result;
            this.data = data;
            this.cause = cause;
        }

        public static <R> ResultSet<R> newInstance(boolean result, R data, Throwable cause) {
            return new ResultSet<>(result, data, cause);
        }

    }
}
