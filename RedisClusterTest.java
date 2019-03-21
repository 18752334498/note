package com.royasoft.vwt.ui.controller.config;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {
    
    private static String host = "192.168.42.128";
    
    public static void main(String[] args) throws Exception {
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxTotal(100); 
        config.setMaxIdle(50); 
        config.setMinIdle(20); 
        config.setMaxWaitMillis(6 * 1000); 
        config.setTestOnBorrow(true); 
        // Redis集群的节点集合
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort(host, 8001));
        jedisClusterNodes.add(new HostAndPort(host, 8002));
        jedisClusterNodes.add(new HostAndPort(host, 8003));
        jedisClusterNodes.add(new HostAndPort(host, 8004));
        jedisClusterNodes.add(new HostAndPort(host, 8005));
        jedisClusterNodes.add(new HostAndPort(host, 8006));
        // 根据节点集创集群链接对象
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 2000, 100,config);
        String set = jedisCluster.set("jack".getBytes(), "rose".getBytes());
        System.out.println(set);
        System.out.println(new String(jedisCluster.get("age".getBytes())));
        jedisCluster.close();
    };
}
