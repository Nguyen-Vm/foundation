package org.linker.foundation.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.linker.foundation.dto.StringEnum;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author RWM
 * @date 2018/4/2
 * @description:
 */
public class BeanUtils {
    private static final Logger LOG = LoggerFactory.getLogger(BeanUtils.class);
    private static final ConcurrentMap<Class<?>, ConcurrentMap<String, Field>> cfMap = Maps.newConcurrentMap();
    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(ctx -> ctx.getSource() != null
                        && StringUtils.isNotBlank(ctx.getSource().toString())
                        && ctx.getSourceType().equals(ctx.getDestinationType()));
    }

    /**
     * 对象克隆
     **/
    public static <Source, Target> Target castTo(final Source source, final Class<Target> destType) {
        if (source == null || destType == null) {
            return null;
        }
        try {
            Target target = destType.newInstance();
            map(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("map error", e);
        }
    }

    /**
     * 集合对象克隆
     **/
    public static <Source, Target> List<Target> castTo(final Collection<Source> sources, final Class<Target> targetType) {
        List<Target> targets = Lists.newArrayListWithExpectedSize(sources.size());
        for (Source source : sources) {
            targets.add(castTo(source, targetType));
        }
        return targets;
    }

    private static <Source, Target> void map(final Object source, final Object target) {
        if (source != null && target != null) {
            modelMapper.map(source, target);
        }
    }

    /**
     * Map转Bean
     **/
    public static <T> T map2Bean(final Map<String, Object> source, final Class<T> clazz) {
        return JsonUtils.parseObject(new JSONObject(source).toString(), clazz);
    }

    /**
     * Bean转Map
     **/
    public static Map<String, Object> bean2Map(Object source) {
        return (JSONObject) JSON.toJSON(source);
    }

    /**
     * 查找对象中的字段
     **/
    public static Field findField(final Class<?> clz, final String fieldName) {
        ConcurrentMap<String, Field> fcMap = cfMap.get(clz);
        if (CollectionUtils.isNullOrEmpty(fcMap)) {
            fcMap = Maps.newConcurrentMap();
            return findField(clz, fieldName, fcMap);

        } else {
            Field field = fcMap.get(fieldName);
            if (null != field) {
                return field;
            }
            return findField(clz, fieldName, fcMap);
        }
    }

    private static Field findField(final Class<?> clz, final String fieldName, final ConcurrentMap<String, Field> fcMap) {
        Field field = null;
        try {
            field = clz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            LOG.trace("class: %s covert field %s error.....", clz.getName(), fieldName, e);
            if (clz.getSuperclass() != null) {
                field = findField(clz.getSuperclass(), fieldName);
            }
        }
        if (null != field) {
            fcMap.put(fieldName, field);
            cfMap.put(clz, fcMap);
        }
        return field;
    }

    /**
     * 取对象字段值
     **/
    public static Object getProperty(final Object bean, final String name) {
        try {
            Field field = FieldUtils.getField(bean.getClass(), name, true);
            return field.get(bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置对象属性值
     **/
    public static void setProperty(final Object bean, final String name, final Object value) {
        Field field = FieldUtils.getField(bean.getClass(), name, true);
        try {
            if (null == value) {
                return;
            }
            Class<?> fClazz = field.getType();
            Class<?> vClazz = value.getClass();
            if ((StringEnum.class.isAssignableFrom(fClazz) || Enum.class.isAssignableFrom(fClazz)) && vClazz.equals(String.class)) {
                field.set(bean, Enum.valueOf((Class<? extends Enum>) fClazz, (String) value));
            } else if (fClazz.equals(vClazz) || fClazz.isAssignableFrom(vClazz)) {
                field.set(bean, value);
            } else {
                if (isPrimitiveType(vClazz) && isPrimitiveType(fClazz)) {
                    field.set(bean, JsonUtils.parseObject(JsonUtils.toJSONBytes(value), fClazz));
                } else {
                    LOG.warn("value type %s can not cast to field type %s", vClazz, fClazz);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断是否基本类型
     **/
    public static boolean isPrimitiveType(final Class<?> clz) {
        return clz.isPrimitive()
                || isSubTypeOf(clz, Number.class, Boolean.class, Character.class, String.class, Date.class, LocalDate.class, LocalDateTime.class);
    }

    public static boolean isSubTypeOf(final Class targetClz, final Class... parentClz) {
        for (Class c : parentClz) {
            if (c.isAssignableFrom(targetClz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 合并request至model
     */
    public static Object combineModel(Object model, Object request) {
        Class modelClass = model.getClass();
        Class requestClass = request.getClass();
        Field[] requestFields = requestClass.getDeclaredFields();
        for (Field requestField : requestFields) {
            requestField.setAccessible(true);
            Field modelField = FieldUtils.getField(modelClass, requestField.getName(), true);
            if (modelField != null) {
                try {
                    // BeanUtils.setProperty(model, modelField.getName(), requestField.get(request));
                    modelField.set(model, requestField.get(request));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }


}
