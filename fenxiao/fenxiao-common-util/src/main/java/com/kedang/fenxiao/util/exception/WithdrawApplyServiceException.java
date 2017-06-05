/**************************************************************************
 * Copyright (c) 2014-2023  杭州学信科技有限公司
 * All rights reserved.
 * <p/>
 * 项目名称：fyd-cms
 * 版权说明：本软件属杭州学信科技有限公司所有，在未获杭州学信科技有限公司正式授权
 * 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受
 * 知识产权保护的内容。
 ***************************************************************************/
package com.kedang.fenxiao.util.exception;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.common.Redis4WithdrawApplyHashMap;

/**
 * @description com.xuexin.fyd.util.exception
 * @author zhangqi@fuyidai.me
 * @version 1.0.0
 * @since 2016/1/13.
 */
@SuppressWarnings("serial")
@Component
@Scope(value = "prototype")
public class WithdrawApplyServiceException extends ServiceException {
    private Long id;
    private int code;
    private String message;
    private Redis4WithdrawApplyHashMap redis;

    public WithdrawApplyServiceException() {
        super();
    }

    public WithdrawApplyServiceException(String message) {
        super(message);
        this.message = message;
    }

    public WithdrawApplyServiceException(Redis4WithdrawApplyHashMap redis,Long id, String message) {
        super(message);
        this.redis = redis;
        this.redis.remove(id.toString());
        this.message = message;
        this.id = id;
    }

    public WithdrawApplyServiceException(Redis4WithdrawApplyHashMap redis,Long id, int code, String message) {
        super(message, code);
        this.redis = redis;
        this.redis.remove(id.toString());
        this.message = message;
        this.code = code;
        this.id = id;
    }

    public WithdrawApplyServiceException(Throwable cause) {
        super(cause);
    }

    public WithdrawApplyServiceException(String message, Throwable cause) {
        super(message, cause);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Redis4WithdrawApplyHashMap getRedis() {
        return redis;
    }

    public void setRedis(Redis4WithdrawApplyHashMap redis) {
        this.redis = redis;
    }
}



