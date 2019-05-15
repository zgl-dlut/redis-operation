package com.zgl.redis.controller;

import com.zgl.redis.service.RedisOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zgl
 * @date 2019/5/15 下午5:53
 */
@RestController
@RequestMapping("/redis")
@Api(tags="redis操作API")
public class RedisController {

	@Autowired
	private RedisOperation redisOperation;

	@ApiOperation(value = "string数据结构")
	@GetMapping("/string/set")
	public void setString(@RequestParam String key, @RequestParam String value) {
		redisOperation.setString(key, value);
	}

	@GetMapping("/string/get")
	public String getString(@RequestParam String key) {
		return redisOperation.getString(key);
	}

	@PostMapping("/list/set")
	public void setList(@RequestHeader String key, @RequestBody List<String> value) {
		redisOperation.setList(key, value);
	}

	@GetMapping("/list/get")
	public List<String> getList(@RequestParam String key) {
		return redisOperation.getList(key);
	}

	@PostMapping("/hash/set")
	public void setHash(@RequestHeader String key, @RequestBody Map<String, Integer> hash) {
		redisOperation.setHash(key, hash);
	}

	@GetMapping("/hash/get/{key}/{hashKey}")
	public Object getHash(@PathVariable String key, @PathVariable String hashKey) {
		return redisOperation.getHash(key, hashKey);
	}

	@PostMapping("/set/set")
	public void setSet(@RequestHeader String key, @RequestBody String[] value) {
		redisOperation.setSet(key, value);
	}

	@GetMapping("/set/get")
	public Object getSet(@RequestParam String key) {
		return redisOperation.getSet(key);
	}

	@PostMapping("/zset/set")
	public void setZSet(@RequestHeader String key, @RequestBody Map<String, Double> value) {
		redisOperation.setZSet(key, value);
	}

	@GetMapping("/zset/get")
	public Object getZSet(@RequestParam String key, @RequestParam String item) {
		return redisOperation.getZSet(key, item);
	}

	@PostMapping("/object/set")
	public void setObject(@RequestHeader String key, @RequestBody Object value) {
		redisOperation.setObject(key, value);
	}
}