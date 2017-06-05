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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description com.xuexin.fyd.common
 * @author zhangqi@fuyidai.me
 * @version 1.0.0
 * @since 2015/12/29.
 */
@Service
public class Redis4WithdrawApplyHashMap {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void save(Map<?,?> data){
        if (data==null){
            return;
        }
        redisTemplate.opsForHash().putAll(RedisType.withdraw_apply_map.toString(),data);
    }

    public void save(Long id,String value){
        if (id==null || value == null){
            return;
        }
        redisTemplate.opsForHash().put(RedisType.withdraw_apply_map.toString(),id.toString(),value);
    }
    public void remove(String field){
        if (field ==null ){
            return;
        }
        redisTemplate.opsForHash().delete(RedisType.withdraw_apply_map.toString(),field);
    }

    public Object find(String field){
        if (field == null){
            return null;
        }
        return redisTemplate.opsForHash().get(RedisType.withdraw_apply_map.toString(),field);
    }

}
