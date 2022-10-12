package br.com.kronos.backend.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableCaching
public class ConfigCacheRedis extends CachingConfigurerSupport{


	@Value("${spring.redis.host}")
    private String redisHostName;

    @Value("${spring.redis.port:}")
    private Integer redisPort;

    @Value("${spring.redis.password:}")
    private String redisPassword;

    @Value("${spring.redis.time-to-live}")
    private long ttl;
    
    @Value("${spring.redis.url:}")
    private String redisUrl;
    

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() throws URISyntaxException {
    	 log.info("Redis (/Lettuce) configuration enabled with cache timeout {} ", ttl, " seconds.");
    	if (!StringUtils.isEmpty(redisUrl)) {
    		URI redistogoUri = new URI(redisUrl);
    		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(redistogoUri.getHost());
            redisStandaloneConfiguration.setPort(redistogoUri.getPort());
            redisStandaloneConfiguration.setPassword(redistogoUri.getUserInfo().split(":", 2)[1]);
            return new LettuceConnectionFactory(redisStandaloneConfiguration);
    	} 
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHostName);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(redisPassword);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
                .build();
    }
    
    @Bean
    public RedisCacheManager redisCacheManager() throws URISyntaxException {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(ttl));
        redisCacheConfiguration.usePrefix();
        redisCacheConfiguration.prefixKeysWith("kronos");

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration).build();

    }
    
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
       StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
       return stringRedisSerializer;
    }
    
    
}
