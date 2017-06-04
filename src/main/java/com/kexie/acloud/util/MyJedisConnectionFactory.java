package com.kexie.acloud.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.Pool;

/**
 * Created by zojian on 2017/6/2.
 */
public class MyJedisConnectionFactory extends JedisConnectionFactory {
    public Jedis getJedis(){
        return this.fetchJedisConnector();
    }

    public MyJedisConnectionFactory() {
        super();
    }

    public MyJedisConnectionFactory(JedisShardInfo shardInfo) {
        super(shardInfo);
    }

    public MyJedisConnectionFactory(JedisPoolConfig poolConfig) {
        super(poolConfig);
    }

    public MyJedisConnectionFactory(RedisSentinelConfiguration sentinelConfig) {
        super(sentinelConfig);
    }

    public MyJedisConnectionFactory(RedisSentinelConfiguration sentinelConfig, JedisPoolConfig poolConfig) {
        super(sentinelConfig, poolConfig);
    }

    public MyJedisConnectionFactory(RedisClusterConfiguration clusterConfig) {
        super(clusterConfig);
    }

    public MyJedisConnectionFactory(RedisClusterConfiguration clusterConfig, JedisPoolConfig poolConfig) {
        super(clusterConfig, poolConfig);
    }

    @Override
    protected Jedis fetchJedisConnector() {
        return super.fetchJedisConnector();
    }

    @Override
    protected JedisConnection postProcessConnection(JedisConnection connection) {
        return super.postProcessConnection(connection);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }

    @Override
    protected Pool<Jedis> createRedisSentinelPool(RedisSentinelConfiguration config) {
        return super.createRedisSentinelPool(config);
    }

    @Override
    protected Pool<Jedis> createRedisPool() {
        return super.createRedisPool();
    }

    @Override
    protected JedisCluster createCluster(RedisClusterConfiguration clusterConfig, GenericObjectPoolConfig poolConfig) {
        return super.createCluster(clusterConfig, poolConfig);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public RedisConnection getConnection() {
        return super.getConnection();
    }

    @Override
    public RedisClusterConnection getClusterConnection() {
        return super.getClusterConnection();
    }

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return super.translateExceptionIfPossible(ex);
    }

    @Override
    public String getHostName() {
        return super.getHostName();
    }

    @Override
    public void setHostName(String hostName) {
        super.setHostName(hostName);
    }

    @Override
    public void setUseSsl(boolean useSsl) {
        super.setUseSsl(useSsl);
    }

    @Override
    public boolean isUseSsl() {
        return super.isUseSsl();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public int getPort() {
        return super.getPort();
    }

    @Override
    public void setPort(int port) {
        super.setPort(port);
    }

    @Override
    public JedisShardInfo getShardInfo() {
        return super.getShardInfo();
    }

    @Override
    public void setShardInfo(JedisShardInfo shardInfo) {
        super.setShardInfo(shardInfo);
    }

    @Override
    public int getTimeout() {
        return super.getTimeout();
    }

    @Override
    public void setTimeout(int timeout) {
        super.setTimeout(timeout);
    }

    @Override
    public boolean getUsePool() {
        return super.getUsePool();
    }

    @Override
    public void setUsePool(boolean usePool) {
        super.setUsePool(usePool);
    }

    @Override
    public JedisPoolConfig getPoolConfig() {
        return super.getPoolConfig();
    }

    @Override
    public void setPoolConfig(JedisPoolConfig poolConfig) {
        super.setPoolConfig(poolConfig);
    }

    @Override
    public int getDatabase() {
        return super.getDatabase();
    }

    @Override
    public void setDatabase(int index) {
        super.setDatabase(index);
    }

    @Override
    public String getClientName() {
        return super.getClientName();
    }

    @Override
    public void setClientName(String clientName) {
        super.setClientName(clientName);
    }

    @Override
    public boolean getConvertPipelineAndTxResults() {
        return super.getConvertPipelineAndTxResults();
    }

    @Override
    public void setConvertPipelineAndTxResults(boolean convertPipelineAndTxResults) {
        super.setConvertPipelineAndTxResults(convertPipelineAndTxResults);
    }

    @Override
    public boolean isRedisSentinelAware() {
        return super.isRedisSentinelAware();
    }

    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return super.getSentinelConnection();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
