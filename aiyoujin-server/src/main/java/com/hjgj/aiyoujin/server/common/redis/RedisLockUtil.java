package com.hjgj.aiyoujin.server.common.redis;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisLockUtil extends AbstractBaseRedisAdapter<String, String>{
	private Logger logger = Logger.getLogger(RedisLockUtil.class);
	
	/***
	 * 获取共享锁
	 * @param lock 锁的key
	 * @param expired 超时时间
	 * @return boolean
	 */
	public boolean acquireLock(String lock,long expired) {
	    // 1. 通过SETNX试图获取一个lock
	    boolean success = false;
	    long value = System.currentTimeMillis() + expired + 1;     
	    boolean acquired = add(lock, String.valueOf(value),expired/1000);
	    //SETNX成功，则成功获取一个锁
	    if (acquired)      
	        success = true;
	    //SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
	    else {
	    	String oldValueStr = get(lock);
	    	if(StringUtils.isNotBlank(oldValueStr)){
	    		long oldValue = Long.valueOf(oldValueStr);
		        //超时
		        if (oldValue < System.currentTimeMillis()) {
		        	//超时之后删除共享锁,让后重新锁定
		        	delete(lock);
		        	boolean acquired2 = add(lock, String.valueOf(value),expired/1000);         
		            // 获取锁成功
		            if (acquired2) 
		                success = true;
		            // 已被其他进程捷足先登了
		            else 
		                success = false;
		        }//未超时，则直接返回失败
		        else             
		            success = false;
	    	}else             
	            success = false;

	    }        
	    return success;      
	}
	 
	//释放锁
	public void releaseLock(String key) {
	    long current = System.currentTimeMillis();    
	    String oldValueStr = get(key);
	    if(oldValueStr != null){
	    	 long oldValue = Long.valueOf(oldValueStr);
	 	    // 避免删除非自己获取得到的锁
	 	    if (current < oldValue){
	 	    	delete(key);
				logger.info("释放分布共享锁" + key + "【成功】。。。");
	 	    }
	    }
	}

	
	 /**  
     * 新增 
     *<br>------------------------------<br> 
     * @param user 
     * @return 
     */  
    public boolean add(final String key,final String value,final long expireSeconds) {  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	String key1 = key;
            	String value1 = value;
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(key1);  
                byte[] value = serializer.serialize(value1);  
                Boolean bl = connection.setNX(key, value);
                connection.expire(key, expireSeconds);
                return bl;
            }  
        });  
        return result;  
    }  
      
    /**  
     * 删除 
     * <br>------------------------------<br> 
     * @param key 
     */  
    public void delete(String key) {  
        List<String> list = new ArrayList<String>();  
        list.add(key);  
        delete(list);  
    }  
  
    /** 
     * 删除多个 
     * <br>------------------------------<br> 
     * @param keys 
     */  
    public void delete(List<String> keys) {  
        redisTemplate.delete(keys);  
    }  
  
    /**  
     * 通过key获取 
     * <br>------------------------------<br> 
     * @param keyId 
     * @return 
     */  
    public String get(final String keyId) {  
        String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key = serializer.serialize(keyId);  
                byte[] value = connection.get(key);  
                if (value == null) {  
                    return null;  
                }  
                String result = serializer.deserialize(value);  
                return result;  
            }  
        });  
        return result;  
    }
    
    /**  
     * 新增 
     *<br>------------------------------<br> 
     * @param user 
     * @return 
     */  
    public String getSet(final String key,final String value) {  
    	String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	String key1 = key;
            	String value1 = value;
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(key1);  
                byte[] value = serializer.serialize(value1);
                byte[] result = connection.getSet(key, value);
                return result==null?null:serializer.deserialize(result);  
            }  
        });  
        return result;  
    }
}
