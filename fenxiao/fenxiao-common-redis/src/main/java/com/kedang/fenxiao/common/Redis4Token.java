package com.kedang.fenxiao.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class Redis4Token
{
	@Autowired
	private StringRedisTemplate redisTemplate;

	public void save(String key, String value)
	{
		redisTemplate.opsForValue().set(key, value);
	}

	public String get(String key)
	{
		return redisTemplate.opsForValue().get(key);
	}

	public void remove(String key)
	{
		redisTemplate.delete(key);
	}
}
