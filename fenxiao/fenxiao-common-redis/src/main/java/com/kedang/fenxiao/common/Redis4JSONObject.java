/**************************************************************************
 * Copyright (c) 2014-2023  杭州学信科技有限公司
 * All rights reserved.
 * <p>
 * 项目名称：fyd-cms
 * 版权说明：本软件属杭州学信科技有限公司所有，在未获杭州学信科技有限公司正式授权
 * 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受
 * 知识产权保护的内容。
 ***************************************************************************/
package com.kedang.fenxiao.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqi@fuyidai.me
 * @version V1.0.0
 * @date 2015/12/29
 */
@Service
public class Redis4JSONObject {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 保存JSON对象到redis
     *
     * @param type
     * @param id
     * @param data
     * @throws Throwable
     */
    public void save(RedisType type, Integer id, JSONObject data) throws Throwable {
        if (type == null || id == null || data == null) {
            return;
        }
        //保存对象
        redisTemplate.opsForValue().set(type.toString() + id, data.toJSONString());
    }

    /**
     * 保存JSON对象到redis
     *
     * @param type
     * @param id
     * @param data
     * @throws Throwable
     */
    public void save(RedisType type, Integer id, Object data) throws Throwable {
        if (type == null || id == null || data == null) {
            return;
        }
        //保存对象
        redisTemplate.opsForValue().set(type.toString() + id, JSONObject.toJSONString(data));
    }

    /**
     * 保存JSON对象到redis
     *
     * @param type
     * @param id
     * @param data
     * @throws Throwable
     */
    public void save(RedisType type, String id, JSONObject data) throws Throwable {
        if (type == null || id == null || data == null) {
            return;
        }
        //保存对象
        redisTemplate.opsForValue().set(type.toString() + id, data.toJSONString());
    }

    /**
     * 保存JSON对象到redis
     *
     * @param type
     * @param id
     * @param data
     * @throws Throwable
     */
    public void save(RedisType type, Integer id, JSONObject data, Integer timeOut) throws Throwable {
        if (type == null || id == null || data == null || timeOut == null) {
            return;
        }
        //保存对象
        redisTemplate.opsForValue().set(type.toString() + id, data.toJSONString(), timeOut, RedisConfigure.REDIS_TIMEOUT_UNIT);
    }

    /**
     * 保存JSON对象到redis
     *
     * @param type
     * @param id
     * @param data
     * @throws Throwable
     */
    public void save(RedisType type, Integer id, Object data, Integer timeOut) throws Throwable {
        if (type == null || id == null || data == null || timeOut == null) {
            return;
        }
        //保存对象
        redisTemplate.opsForValue().set(type.toString() + id, JSONObject.toJSONString(data), timeOut, RedisConfigure.REDIS_TIMEOUT_UNIT);
    }

    /**
     * 保存JSON对象到redis
     *
     * @param type
     * @param id
     * @param data
     * @throws Throwable
     */
    public void save(RedisType type, String id, JSONObject data, Integer timeOut) throws Throwable {
        if (type == null || id == null || data == null || timeOut == null) {
            return;
        }
        //保存对象
        redisTemplate.opsForValue().set(type.toString() + id, data.toJSONString(), timeOut, RedisConfigure.REDIS_TIMEOUT_UNIT);
    }

    /**
     * 删除对象
     *
     * @param type
     * @param id
     * @throws Throwable
     */
    public void remove(RedisType type, Integer id) throws Throwable {
        if (type == null || id == null) return;
        redisTemplate.delete(type.toString() + id);
    }

    /**
     * 删除对象
     *
     * @param type
     * @param id
     * @throws Throwable
     */
    public void remove(RedisType type, String id) throws Throwable {
        if (type == null || id == null) return;
        redisTemplate.delete(type.toString() + id);
    }

    /**
     * 根据键删除JSON对象
     *
     * @param idList
     * @throws Throwable
     */
    public void remove(RedisType type, List<String> idList) throws Throwable {
        if (idList == null || idList.size() == 0) return;
        String typeStr = type.toString();
        for (int i = 0; i < idList.size(); i++) {
            String id = idList.get(i);
            idList.set(i, typeStr + id);
        }
        redisTemplate.delete(idList);
    }

    /**
     * 根据键查找JSON对象
     *
     * @throws Throwable
     */
    public JSONObject find(RedisType type, Integer id) throws Throwable {
        if (type == null || id == null) return null;
        String str = redisTemplate.opsForValue().get(type.toString() + id);
        if (!StringUtils.isBlank(str)) {
            return JSONObject.parseObject(str);
        } else {
            return null;
        }
    }

    /**
     * 根据键查找JSON对象
     *
     * @throws Throwable
     */
    public <T> T find(RedisType type, Integer id, Class<T> tClass) throws Throwable {
        if (type == null || id == null) return null;
        String str = redisTemplate.opsForValue().get(type.toString() + id);
        if (!StringUtils.isBlank(str)) {
            return JSONObject.parseObject(str, tClass);
        } else {
            return null;
        }
    }

    /**
     * 根据键查找JSON对象
     *
     * @throws Throwable
     */
    public JSONObject find(RedisType type, String id) throws Throwable {
        if (type == null || id == null) return null;
        String str = redisTemplate.opsForValue().get(type.toString() + id);
        if (!StringUtils.isBlank(str)) {
            return JSONObject.parseObject(str);
        } else {
            return null;
        }
    }

    /**
     * 查找集合
     *
     * @param ids --ID列表
     * @return
     * @throws Throwable
     */
    public List<JSONObject> find(RedisType type, Integer... ids) throws Throwable {
        if (ids == null || ids.length == 0) return null;
        String typeStr = type.toString();
        List<String> keyList = new ArrayList<>();
        for (Integer id : ids) {
            keyList.add(typeStr + id);
        }
        List<String> list = redisTemplate.opsForValue().multiGet(keyList);
        List<JSONObject> resutl = new ArrayList<>();
        for (String str : list) {
            resutl.add(JSONObject.parseObject(str));
        }
        return resutl;
    }

    /**
     * 查找集合
     *
     * @param ids --ID列表
     * @return
     * @throws Throwable
     */
    public <T> List<T> find(RedisType type, Class<T> tClass, Integer... ids) throws Throwable {
        if (ids == null || ids.length == 0) return null;
        String typeStr = type.toString();
        List<String> keyList = new ArrayList<>();
        for (Integer id : ids) {
            keyList.add(typeStr + id);
        }
        List<String> list = redisTemplate.opsForValue().multiGet(keyList);
        List<T> resutl = new ArrayList<>();
        for (String str : list) {
            resutl.add(JSONObject.parseObject(str, tClass));
        }
        return resutl;
    }

    /**
     * 查找集合
     *
     * @param ids --ID列表
     * @return
     * @throws Throwable
     */
    public List<JSONObject> find(RedisType type, String... ids) throws Throwable {
        if (ids == null || ids.length == 0) return null;
        String typeStr = type.toString();
        List<String> keyList = new ArrayList<>();
        for (String id : ids) {
            keyList.add(typeStr + id);
        }
        List<String> list = redisTemplate.opsForValue().multiGet(keyList);
        List<JSONObject> resutl = new ArrayList<>();
        for (String str : list) {
            resutl.add(JSONObject.parseObject(str));
        }
        return resutl;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setExpire(RedisType type, Integer id, Integer timeOut) {
        redisTemplate.expire(type.toString() + id, timeOut, RedisConfigure.REDIS_TIMEOUT_UNIT);
    }
}
