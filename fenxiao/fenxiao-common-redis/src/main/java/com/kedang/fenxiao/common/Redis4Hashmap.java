/**************************************************************************
 * Copyright (c) 2014-2023  杭州学信科技有限公司
 * All rights reserved.
 * <p/>
 * 项目名称：fyd-cms
 * 版权说明：本软件属杭州学信科技有限公司所有，在未获杭州学信科技有限公司正式授权
 * 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受
 * 知识产权保护的内容。
 ***************************************************************************/
package com.kedang.fenxiao.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description com.xuexin.fyd.common
 * @author zhangqi@fuyidai.me
 * @version 1.0.0
 * @since 2016/2/23.
 */
@Service
public class Redis4Hashmap {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void save(RedisType redisType, Map<?,?> data){
        if (data==null || data.size()==0 || redisType == null){
            return;
        }
        redisTemplate.opsForHash().putAll(redisType.toString(),data);
    }

    public void save(RedisType redisType, String field, String value){
        if (redisType==null || StringUtils.isBlank(value) || StringUtils.isBlank(field)){
            return;
        }
        redisTemplate.opsForHash().put(redisType.toString(),field,value);
    }
    public void remove(RedisType redisType, String field){
        if (StringUtils.isBlank(field) || redisType == null ){
            return;
        }
        redisTemplate.opsForHash().delete(redisType.toString(),field);
    }

    public Object find(RedisType redisType, String field){
        if (StringUtils.isBlank(field)||redisType == null){
            return null;
        }
        return redisTemplate.opsForHash().get(redisType.toString(),field);
    }
}
