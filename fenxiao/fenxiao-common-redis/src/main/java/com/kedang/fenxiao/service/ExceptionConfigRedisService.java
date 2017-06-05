/**************************************************************************
 * Copyright (c) 2014-2023  杭州学信科技有限公司
 * All rights reserved.
 * <p/>
 * 项目名称：fyd
 * 版权说明：本软件属杭州学信科技有限公司所有，在未获杭州学信科技有限公司正式授权
 * 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受
 * 知识产权保护的内容。
 ***************************************************************************/
package com.kedang.fenxiao.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @description 异常统计信息
 * @author zhangqi@fuyidai.me
 * @version 1.0.0
 * @since 2016/1/15.
 */
@Service
public class ExceptionConfigRedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void save(Class<? extends Exception> eClass){
        if (eClass == null){
            return;
        }
        String key = eClass.getName();
        String cacheTimes = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(cacheTimes)){
            redisTemplate.opsForValue().set(key,"1");
        }else {
            redisTemplate.opsForValue().increment(key,1l);
        }

    }

    public Long getTimes(Class<? extends Exception> eClass){
        if (eClass == null){
            return 0l;
        }
        String key = eClass.getName();
        String cacheTimes = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(cacheTimes)){
            return 0l;
        }else {
           return Long.parseLong(cacheTimes);
        }
    }

}
