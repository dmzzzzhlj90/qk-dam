package com.qk.redis.config;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.stream.Collectors;

import static io.lettuce.core.ReadFrom.REPLICA_PREFERRED;

@Configuration
class RedisConnFactoryConfig {

    @Bean
    @Primary
    public RedisConnectionFactory standaloneConfig(RedisProperties redisProperties) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * Lettuce 哨兵
     */
    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisConnectionFactory lettuceConnectionFactory(RedisProperties redisProperties, LettuceClientConfiguration lettuceClientConfiguration) {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
        sentinelConfig.setMaster(redisProperties.getSentinel().getMaster());
        sentinelConfig.setSentinels(
                redisProperties.getSentinel().getNodes()
                        .stream().map(s -> {
                    RedisNode redisNode = new RedisNode(s.split(":")[0], Integer.parseInt(s.split(":")[1]));
                    return redisNode;
                })
                        .collect(Collectors.toList())
        );
        sentinelConfig.setPassword(redisProperties.getPassword());
        return new LettuceConnectionFactory(sentinelConfig,lettuceClientConfiguration);
    }

    /**
     * 集群模式
     * @param redisProperties 配置
     * @return RedisConnectionFactory
     */
    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisConnectionFactory cluster(RedisProperties redisProperties,
                                          LettuceClientConfiguration lettuceClientConfiguration){

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        redisClusterConfiguration.setPassword(redisProperties.getPassword());
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration,lettuceClientConfiguration);
        lettuceConnectionFactory.setValidateConnection(true);
        return lettuceConnectionFactory;
    }

    /**
     * 读从写主
     * @param redisProperties 配置
     * @param poolConfig 对象池
     * @return RedisConnectionFactory
     */
    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisConnectionFactory writeToMasterReadFromReplica(RedisProperties redisProperties,
                                                               GenericObjectPoolConfig<Object> poolConfig) {
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .readFrom(REPLICA_PREFERRED)
                .build();
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        redisClusterConfiguration.setPassword(redisProperties.getPassword());
        return new LettuceConnectionFactory(redisClusterConfiguration, clientConfig);
    }

    /**
     * common pool对象池
     * @param redisProperties 读取配置
     * @return GenericObjectPoolConfig
     */
    @Bean
    @Primary
    public GenericObjectPoolConfig<Object> genericObjectPoolConfig(RedisProperties redisProperties){
        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        return poolConfig;
    }

    @Bean
    @Primary
    public LettuceClientConfiguration lettuceClient(RedisProperties redisProperties){
        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());

        return LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .build();
    }
}
