package org.linker.foundation.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author RWM
 * @date 2018/1/28
 * @description 配置文件工具类
 */
public class PropertiesUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtils.class);

    private static Properties props;

    static {
        String fileName = "redis.properties";
        props = new Properties();
        try {
            props.load(new ClassPathResource(fileName).getInputStream());
        } catch (IOException e) {
            LOG.error("配置文件读取异常", e);
        }
    }

    public static String getProperty(String key) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }

}

