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

import java.util.concurrent.TimeUnit;

/**
 * @description 接口访问限制
 * @author zhangqi@fuyidai.me
 * @version 1.0.0
 * @since 2016/1/15.
 */
@Service
public class RedisInterfaceLimitService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取用户调用接口次数
     * @param interfaceName
     * @param userId
     * @return
     */
    public synchronized Long getInterfaceInvokeTimes(String interfaceName,Long userId){
        if (StringUtils.isBlank(interfaceName)||userId == null){
            return 0l;
        }
        String cacheTimes = redisTemplate.opsForValue().get(interfaceName+userId.toString());
        if (StringUtils.isBlank(cacheTimes)){
            return 0l;
        }else {
            return Long.parseLong(cacheTimes);
        }
    }

    /**
     * 保存用户访问接口次数
     * @param interfaceName
     * @param userId
     */
    public synchronized void setInterfaceInvokeTimes(String interfaceName,Long userId){
        if (StringUtils.isBlank(interfaceName)||userId == null){
            return;
        }
        String key = interfaceName+userId.toString();
        String cacheTimes = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(cacheTimes)){
            redisTemplate.opsForValue().set(key,"1",30, TimeUnit.SECONDS);
        }else {
           redisTemplate.opsForValue().increment(key,1l);
        }

    }

    /**
     * 判断用户是否频繁访问接口，30秒内只能访问1次
     * @param interfaceName
     * @param userId
     * @return
     */
    public synchronized boolean isValidInvoke(String interfaceName,Long userId){
        if (StringUtils.isBlank(interfaceName)||userId == null){
            return false;
        }
        Long times = getInterfaceInvokeTimes(interfaceName,userId);
        if (times<2){
            return true;
        }else {
            return false;
        }
    }
}
