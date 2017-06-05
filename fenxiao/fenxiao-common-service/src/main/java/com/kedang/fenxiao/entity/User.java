/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User extends IdEntity{		

	public User(){
		super();
	}

	public User(
		Long id
	){
		this.id = id;
	}
    /**
	*手机号码
	*/
    @Column(name="mphone") 	 
	@Length(max=32)
	private String mphone;
	
    /**
	*支付密码
	*/
    @Column(name="pay_pwd") 	 
	@Length(max=64)
    @JsonIgnore
	private String payPwd;
	
    /**
	*密码
	*/
    @Column(name="pwd") 	 
	@Length(max=64)
    @JsonIgnore
	private String pwd;
	
    /**
	*salt
	*/
    @Column(name="salt") 	 
	@Length(max=32)
    @JsonIgnore
	private String salt;
	
    /**
	*姓名
	*/
    @Column(name="name") 	 
	@Length(max=16)
	private String name;
    
    /**
     * 用户头像
     */
    @Column(name="avatar") 
	private String avatar;
    
    /**
	*姓别 1:男 2:女
	*/
    @Column(name="sex") 	 
	@Max(127)
	private Integer sex;
	
	/**
	*籍贯
	*/
    @Column(name="home_town") 	 
	private String homeTown;
	
    /**
	*状态 1:有效 2:禁用
	*/
    @Column(name="status") 	 
	@Max(127)
	private Integer status;
	
	
    /**
     *认证类型
     */
    @Column(name="authenticate_type") 	 
    @Max(9999999999L)
    private Integer authenticateType;
    /**
	*认证状态
	*/
    @Column(name="authenticate_status") 	 
	@Max(9999999999L)
	private Integer authenticateStatus;
    /**
     * 操作员id
     */
    @Column(name="admin_id")
    private Long admin;
    /**
	*基础认证操作员
	*/
    @ManyToOne
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="first_admin_id")
    private AdminInfo firstAdmin;
    /**
     * 高级认证操作员
     */
    @ManyToOne
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="second_admin_id")
    private AdminInfo secondAdmin;
    /**
     * 认证失败说明
     */
    @Column(name="auth_fail_msg")
    @Length(max=512)
    private String authFailMsg;
    /**
     * 认证提交时间
     */
    @Column(name="auth_submit_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date authSubmitTime;
    /**
	*创建时间
	*/
    @Column(name="create_time") 	 
	
	private java.util.Date createTime;
	
    /**
	*更新时间
	*/
    @Column(name="update_time") 	 
	private java.util.Date updateTime;
    /**
     * 推荐人
     */
    @Column(name="recommended") 	
    private String recommended;
    /**
     * 推荐人记录字段
     */
    @Column(name="recommended_record") 	
    private String recommendedRecord;
    /**
     * 用户账户
     */
	@Transient
    private UserAccount userAccount;

	/**
	 * 支付密码状态 0:未设置 1:已设置
	 */
	@Transient
	private Integer payPwdStatus;
	@Transient
	private Long facultyId;
	
	/**
     * 学校编号
     */
	@Column(name="school_id") 	 
	private Long schoolId;
	
	/**
     * 邮箱
     */
	@Column(name="email") 	 
	@Length(max=128)
	private String email;
	
	/**
     * 是否为新密码
     */
	@Column(name="is_new_pwd") 	 
	private Short isNewPwd;
	
	/**
     * 身份证号
     */
	@Column(name="id_code") 	 
	@Length(max=64)
	private String idCode;
	/**
	 * 可提现总额度
	 */
	@Transient
	private BigDecimal ktxed;
	//columns END
	
	/**
	 * 交易密码输错次数
	 * 默认为0
	 */
	@Transient
	private Short payPwdError=0;
	
	/**
	 * 交易密码输错后解禁时间
	 */
	@Transient
	private Date payPwdFreeTime;

	/**
	 * 专业
	 */
	@Transient
	private String major;
	
	/**
	 * 学校
	 */
	@Transient
	private String academy;
	
	/**
	 * 认证成功时间
	 */
	@Column(name = "authenticate_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date authenticateTime;

	/**
	 * 是否是自动审核
	 */
	@Column(name = "is_auto_check")
	@Max(64)
	private Integer isAutoCheck;
	
	/**
	 * 备注
	 */
	@Column(name="remark")
	@Length(max=1024)
	private String remark;
	
	/**
	 * 操作系统
	 */
	@Transient
	private String device;
	/**
	 * 操作员名字
	 */
	@Transient
	private String adminName;
	/**
	 * 用户等级
	 */
	@Column(name = "user_level")
	private Integer userLevel;

	

	public String getAdminName()
	{
		return adminName;
	}

	public void setAdminName(String adminName)
	{
		this.adminName = adminName;
	}

	public String getDevice()
	{
		return device;
	}

	public void setDevice(String device)
	{
		this.device = device;
	}

	public void setMphone(String value) {
		this.mphone = value;
	}

	public String getMphone() {
		return this.mphone;
	}
	public void setPayPwd(String value) {
		this.payPwd = value;
	}
	
	public String getPayPwd() {
		return this.payPwd;
	}
	public void setPwd(String value) {
		this.pwd = value;
	}
	
	public String getPwd() {
		return this.pwd;
	}
	public void setSalt(String value) {
		this.salt = value;
	}
	
	public String getSalt() {
		return this.salt;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setSex(Integer value) {
		this.sex = value;
	}
	
	public Integer getSex() {
		return this.sex;
	}
	public void setHomeTown(String value) {
		this.homeTown = value;
	}
	
	public String getHomeTown() {
		return this.homeTown;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public void setAuthenticateStatus(Integer value) {
		this.authenticateStatus = value;
	}
	
	public Integer getAuthenticateStatus() {
		return this.authenticateStatus;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public Integer getAuthenticateType() {
		return authenticateType;
	}

	public void setAuthenticateType(Integer authenticateType) {
		this.authenticateType = authenticateType;
	}
	
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public Integer getPayPwdStatus() {
		return payPwdStatus;
	}

	public void setPayPwdStatus(Integer payPwdStatus) {
		this.payPwdStatus = payPwdStatus;
	}

	public String getAuthFailMsg() {
		return authFailMsg;
	}

	public void setAuthFailMsg(String authFailMsg) {
		this.authFailMsg = authFailMsg;
	}

	public AdminInfo getFirstAdmin() {
		return firstAdmin;
	}

	public void setFirstAdmin(AdminInfo firstAdmin) {
		this.firstAdmin = firstAdmin;
	}

	public AdminInfo getSecondAdmin() {
		return secondAdmin;
	}

	public void setSecondAdmin(AdminInfo secondAdmin) {
		this.secondAdmin = secondAdmin;
	}

	public Long getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Long facultyId) {
		this.facultyId = facultyId;
	}
	
	public Date getAuthSubmitTime() {
		return authSubmitTime;
	}

	public void setAuthSubmitTime(Date authSubmitTime) {
		this.authSubmitTime = authSubmitTime;
	}

	public BigDecimal getKtxed() {
		return ktxed;
	}

	public void setKtxed(BigDecimal ktxed) {
		this.ktxed = ktxed;
	}

	public Long getAdmin() {
		return admin;
	}

	public void setAdmin(Long admin) {
		this.admin = admin;
	}

	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}

	public String getRecommendedRecord() {
		return recommendedRecord;
	}

	public void setRecommendedRecord(String recommendedRecord) {
		this.recommendedRecord = recommendedRecord;
	}
	
	public Long getSchoolId()
	{
		return schoolId;
	}

	public void setSchoolId(Long schoolId)
	{
		this.schoolId = schoolId;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Short getIsNewPwd()
	{
		return isNewPwd;
	}

	public void setIsNewPwd(Short isNewPwd)
	{
		this.isNewPwd = isNewPwd;
	}

	public String getIdCode()
	{
		return idCode;
	}

	public void setIdCode(String idCode)
	{
		this.idCode = idCode;
	}
	
	public Short getPayPwdError()
	{
		return payPwdError;
	}

	public void setPayPwdError(Short payPwdError)
	{
		this.payPwdError = payPwdError;
	}

	public Date getPayPwdFreeTime()
	{
		return payPwdFreeTime;
	}

	public void setPayPwdFreeTime(Date payPwdFreeTime)
	{
		this.payPwdFreeTime = payPwdFreeTime;
	}

	public String getMajor()
	{
		return major;
	}

	public void setMajor(String major)
	{
		this.major = major;
	}

	public String getAcademy()
	{
		return academy;
	}

	public void setAcademy(String academy)
	{
		this.academy = academy;
	}

	public Date getAuthenticateTime()
	{
		return authenticateTime;
	}

	public void setAuthenticateTime(Date authenticateTime)
	{
		this.authenticateTime = authenticateTime;
	}

	public Integer getIsAutoCheck()
	{
		return isAutoCheck;
	}

	public void setIsAutoCheck(Integer isAutoCheck)
	{
		this.isAutoCheck = isAutoCheck;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Mphone",getMphone())
			.append("PayPwd",getPayPwd())
			.append("Pwd",getPwd())
			.append("Salt",getSalt())
			.append("Name",getName())
			.append("Sex",getSex())
			.append("HomeTown",getHomeTown())
			.append("Status",getStatus())
			.append("AuthenticateStatus",getAuthenticateStatus())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}




	
	public boolean equals(Object obj) {
		if(obj instanceof User == false) return false;
		if(this == obj) return true;
		User other = (User)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	public String getSexString(){
		 if(sex!=null&&sex==1){
				return "男";
			}else if(sex!=null&&sex==2){
				return "女";
			}else{
				return "";
			}
	}

}