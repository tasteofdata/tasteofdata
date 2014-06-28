package com.tasteofdata.land.module.support.cache.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.log4j.Logger;

import com.tasteofdata.land.module.support.cache.CacheService;

/**
 * memcache实现
 * @author wwj
 *
 */
public class CacheServiceMemcacheImpl implements CacheService{
	private static final Logger logger = Logger.getLogger(CacheServiceMemcacheImpl.class);
	public static final String CACHE_PROP_FILE = "memcache";
	public static final String ENCODING = "UTF-8";
	private int coolPoolSize ;		//客户端连接个数，默认是50
	private String servers ;		//连接地址以，逗号分隔
	private String weights;		//权重，以逗号分隔
	private XMemcachedClientBuilder builder;
	private MemcachedClient client;
	
	public void initialize(){
		//weight
		String[] weight_strarry = weights.split(",");
		int weight[] = new int[weight_strarry.length];
		for(int i=0;i<weight_strarry.length; i++)
			weight[i] = Integer.parseInt(weight_strarry[i]);
		
		builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers.replaceAll("," ," ")),weight);
		// 设置连接池大小，即客户端个数  ,默认50个
		if(coolPoolSize<=0){
			coolPoolSize = 50;
		}
        builder.setConnectionPoolSize(coolPoolSize);
        // 宕机报警  
        builder.setFailureMode(true);
        // 使用二进制文件 
        builder.setCommandFactory(new BinaryCommandFactory());
        
        try {
			client = builder.build();
		} catch (IOException e) {
			logger.error("", e);
			throw new RuntimeException();
		}
	}
	
	@Override
	public<T extends Serializable> boolean set(String key, T value, int seconds) {
		try {
			client.set(key, seconds, value);
			return true;
		} catch (TimeoutException e) {
			logger.error("",e);
		} catch (InterruptedException e) {
			logger.error("",e);
		} catch (MemcachedException e) {
			logger.error("",e);
		}
		return false;
	}

	@Override
	public <T extends Serializable> T get(String key) {
		try {
			return client.get(key);
		} catch (TimeoutException e) {
			logger.error("",e);
		} catch (InterruptedException e) {
			logger.error("",e);
		} catch (MemcachedException e) {
			logger.error("",e);
		}
		return null;
	}

	@Override
	public String getString(String key) {
		try {
			return client.get(key);
		} catch (TimeoutException e) {
			logger.error("",e);
		} catch (InterruptedException e) {
			logger.error("",e);
		} catch (MemcachedException e) {
			logger.error("",e);
		}
		return null;
	}

	@Override
	public boolean setString(String key, String value, int seconds) {
		try {
			client.set(key, seconds, value);
			return true;
		} catch (TimeoutException e) {
			logger.error("",e);
		} catch (InterruptedException e) {
			logger.error("",e);
		} catch (MemcachedException e) {
			logger.error("",e);
		}
		return false;
	}

	@Override
	public boolean delete(String key) {
		try {
			return client.delete(key);
		} catch (TimeoutException e) {
			logger.error("", e);
		} catch (InterruptedException e) {
			logger.error("", e);
		} catch (MemcachedException e) {
			logger.error("", e);
		}
		return false;
	}

	public void setCoolPoolSize(int coolPoolSize) {
		this.coolPoolSize = coolPoolSize;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public void setWeights(String weights) {
		this.weights = weights;
	}

}
