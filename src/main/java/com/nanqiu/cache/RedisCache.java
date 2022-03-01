package com.nanqiu.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.nanqiu.utils.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//自定义redis缓存实现
public class RedisCache implements Cache {

    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    //这里使用了redis缓存，使用springboot自动注入
    //private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String id;

    public RedisCache(final String id){
        if (id == null){
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    public RedisTemplate<String, Object> getRedisTemplate(){
        RedisTemplate<String, Object> redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL,JsonTypeInfo.As.WRAPPER_ARRAY);
        serializer.setObjectMapper(mapper);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Override
    public String getId(){
        return this.id;
    }

    @Override
    public void putObject(Object key,Object value){
        if (redisTemplate == null) redisTemplate = getRedisTemplate();
        if (value != null){
            //使用redis hash 类型作为缓存存储模型
            redisTemplate.opsForHash().put(id,getKeyToMD5(key.toString()),value);
        }
    }

    @Override
    public Object getObject(Object key){
        if (redisTemplate == null) redisTemplate = getRedisTemplate();
        System.out.println("id为 ===" + id);
        try {
            if (key != null){
                return redisTemplate.opsForHash().get(id,getKeyToMD5(key.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object removeObject(Object key){
        if (redisTemplate == null) redisTemplate = getRedisTemplate();
        if (key != null){
            redisTemplate.delete(key.toString());
        }
        return null;
    }

    @Override
    public void clear(){
        if (redisTemplate == null) redisTemplate = getRedisTemplate();
        System.out.println("清空缓存" + id);
        if (id.contains("UserMapper")){
            redisTemplate.delete("com.nanqiu.mapper.HistoryMapper");
            redisTemplate.delete("com.nanqiu.mapper.BathroomMapper");
        }
        redisTemplate.delete(id);
    }

    @Override
    public int getSize(){
        if (redisTemplate == null) redisTemplate = getRedisTemplate();
        Long size = redisTemplate.opsForHash().size(id);
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock(){
        return this.readWriteLock;
    }

    //对一个key进行MD5加密
    private String getKeyToMD5(String key){
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
