package com.zgl.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zgl
 * @date 2019/5/15 下午5:53
 */
@Component
@Slf4j
public class RedisOperation {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * String 数据结构
	 */

	public void setString(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value, 60, TimeUnit.MINUTES);
	}

	public String getString(String key) {
		log.error("获取到String数据结构的值是:{}", stringRedisTemplate.opsForValue().get(key));
		return stringRedisTemplate.opsForValue().get(key);
	}


	/**
	 * List 数据结构
	 */

	public void setList(String key, List<String> value) {
		stringRedisTemplate.opsForList().leftPushAll(key, value);
		log.error("List的大小为:{}", stringRedisTemplate.opsForList().size(key));
		log.error("遍历List:{}", stringRedisTemplate.opsForList().range(key, 0, -1));
	}

	public List<String> getList(String key) {
		return stringRedisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * Hash 数据结构
	 */

	@SuppressWarnings("unchecked")
	public void setHash(String key, Map<String, Integer> hash) {
		redisTemplate.opsForHash().putAll(key, hash);
		log.error("hash的值为:{}", redisTemplate.opsForHash().entries(key).toString());
	}

	@SuppressWarnings("unchecked")
	public Object getHash(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	/**
	 * Set 数据结构
	 */

	public void setSet(String key, String[] value) {
		redisTemplate.opsForSet().add(key, value);
		log.error("set大小为:{}", redisTemplate.opsForSet().size(key));
	}

	public Object getSet(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * ZSet 数据结构
	 */

	public void setZSet(String key, Map<String,Double> value) {
		Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
		Set<String>keySet=value.keySet();
		for(String k:keySet){
			ZSetOperations.TypedTuple<Object> objectTypedTuple = new DefaultTypedTuple<>(k, value.get(k));
			tuples.add(objectTypedTuple);
		}
		redisTemplate.opsForZSet().add(key, tuples);
		log.error("zset排序:{}", redisTemplate.opsForZSet().range(key, 0, -1));
	}

	public Object getZSet(String key, String item) {
		return redisTemplate.opsForZSet().rank(key, item);
	}

	/**
	 * 对象 object
	 */
	public void setObject(String key ,Object value){
		redisTemplate.opsForValue().set(key, value);
	}
}