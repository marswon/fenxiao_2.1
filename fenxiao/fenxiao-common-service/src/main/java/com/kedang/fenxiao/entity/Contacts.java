/**************************************************************************
 * Copyright (c) 2014-2023  杭州学信科技有限公司
 * All rights reserved.
 * <p/>
 * 项目名称：fyd
 * 版权说明：本软件属杭州学信科技有限公司所有，在未获杭州学信科技有限公司正式授权
 * 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受
 * 知识产权保护的内容。
 ***************************************************************************/
package com.kedang.fenxiao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * @description com.xuexin.fyd.entity
 * @author zhangqi@fuyidai.me
 * @version 1.0.0
 * @since 2016/2/29.
 */
@Entity
@Table(name = "fyd_contacts")
public class Contacts extends IdEntity {
    /**
     * 用户id
     */
    private Long userId;
    @Length(max = 25)
    /**
     * 联系人手机号码
     */
    private String mobileTel;
    @Length(max = 50)
    /**
     * 联系人姓名-联系人通过认证后的真实姓名
     */
    private String realName;
    /**
     * 联系人状态-0未注册，1已注册，未认证，2已认证
     */
    private Integer status;
    /**
     * 用户对联系人的备注
     */
    @Length(max = 50)
    private String mobileRemark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间-默认为当前时间
     */
    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobileRemark() {
        return mobileRemark;
    }

    public void setMobileRemark(String mobileRemark) {
        this.mobileRemark = mobileRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
