package org.linker.foundation.provider.redis;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.linker.foundation.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by losyn on 3/30/17.
 */
@SuppressWarnings("unused")
public abstract class RedisConfigurationSupport {
    private static final Logger LOG = LoggerFactory.getLogger(RedisConfigurationSupport.class);
    protected String defaultRedisUri;

    @PostConstruct
    protected abstract void initConfiguration();

    @Primary
    @Bean(name = IDynamicRedis.DEFAULT)
    public RedisPairOperations defaultRedisPairOperations() {
        return new RedisPairOperations(defaultStringRedisTemplate());
    }

    @Bean
    @Primary
    public StringRedisTemplate defaultStringRedisTemplate(){
        return new StringRedisTemplate(jedisConnectionFactory(defaultRedisUri));
    }

    /** JEDIS://password@hostname:port/database */
    protected RedisConnectionFactory jedisConnectionFactory(String jedisUri) {
        LOG.info("jedis url: %s", jedisUri);

        List<String> uriList = Splitter.on("@").splitToList(jedisUri);
        if(CollectionUtils.isNullOrEmpty(uriList) || 2 != uriList.size()){
            throw new RuntimeException("jedis uri：" + jedisUri + "  error.....");
        }
        List<String> dbHost = Splitter.on("/").splitToList(uriList.get(1));
        if(CollectionUtils.isNullOrEmpty(uriList) || 2 != uriList.size()){
            throw new RuntimeException("jedis uri：" + jedisUri + "  error.....");
        }
        List<String> hostPost = Splitter.on(":").splitToList(dbHost.get(0));
        if(CollectionUtils.isNullOrEmpty(uriList) || 2 != uriList.size()){
            throw new RuntimeException("jedis uri：" + jedisUri + "  error.....");
        }
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(hostPost.get(0), Integer.parseInt(hostPost.get(1)));
        String password = StringUtils.substringAfter(uriList.get(0), "JEDIS://");
        if (!StringUtils.isBlank(password)) {
            conf.setPassword(RedisPassword.of(password));
        }
        conf.setDatabase(Integer.parseInt(dbHost.get(1)));
        JedisConnectionFactory jedis = new JedisConnectionFactory(conf);
        jedis.afterPropertiesSet();
        return jedis;
    }
}
