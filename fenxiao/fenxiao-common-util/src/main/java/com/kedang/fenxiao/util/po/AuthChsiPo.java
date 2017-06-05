package com.kedang.fenxiao.util.po;


/**
 * 学信网认证po
 * @author ggx
 * @date 2015年7月13日
 */
public class AuthChsiPo
{
	/**
	 * 用户编号
	 */
	private Long userId;

	/**
	 * 学校编号
	 */
	private Long schoolId;

	/**
	 * 学校名称
	 */
	private String schoolName;

	/**
	 * 学信网学籍信息
	 */
	private String xjInfo;

	/**
	 * 学信网注册信息
	 */
	private String registerInfo;

	/**
	 * 失效时间
	 */
	private java.util.Date failureTime;

	/**
	 * 学信网登录名
	 */
	private String loginName;

	/**
	 * 学信网登录密码
	 */
	private String loginPwd;

	/**
	 * 抓取状态
	 */
	private Integer status;

	/**
	 * 抓取异常信息
	 */
	private String errorMsg;

	/**
	 * 地理位置信息
	 */
	private String lbs;

	/**
	 * 备注(审核失败原因)
	 */
	private String memo;
	private String name;
	private String sex;
	private String nation;
	private String birthday;
	private String cardId;
	private String candidateId;
	private String studentId;
	private String academy;
	private String branch;
	private String department;
	private String major;
	private String classs;
	private String degree;
	private String time;
	private String studyType;
	private String majorType;
	private String enterDate;
	private String state;
	private String offDate;
	private String schoolAvatar;
	private String schoolAvatar_2;
	
	public String getSchoolAvatar_2()
	{
		return schoolAvatar_2;
	}

	public void setSchoolAvatar_2(String schoolAvatar_2)
	{
		this.schoolAvatar_2 = schoolAvatar_2;
	}

	public String getNation()
	{
		return nation;
	}

	public void setNation(String nation)
	{
		this.nation = nation;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getCardId()
	{
		return cardId;
	}

	public void setCardId(String cardId)
	{
		this.cardId = cardId;
	}

	public String getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(String candidateId)
	{
		this.candidateId = candidateId;
	}

	public String getStudentId()
	{
		return studentId;
	}

	public void setStudentId(String studentId)
	{
		this.studentId = studentId;
	}

	public String getAcademy()
	{
		return academy;
	}

	public void setAcademy(String academy)
	{
		this.academy = academy;
	}

	public String getBranch()
	{
		return branch;
	}

	public void setBranch(String branch)
	{
		this.branch = branch;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getMajor()
	{
		return major;
	}

	public void setMajor(String major)
	{
		this.major = major;
	}

	public String getClasss()
	{
		return classs;
	}

	public void setClasss(String classs)
	{
		this.classs = classs;
	}

	public String getDegree()
	{
		return degree;
	}

	public void setDegree(String degree)
	{
		this.degree = degree;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getStudyType()
	{
		return studyType;
	}

	public void setStudyType(String studyType)
	{
		this.studyType = studyType;
	}

	public String getMajorType()
	{
		return majorType;
	}

	public void setMajorType(String majorType)
	{
		this.majorType = majorType;
	}

	public String getEnterDate()
	{
		return enterDate;
	}

	public void setEnterDate(String enterDate)
	{
		this.enterDate = enterDate;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getOffDate()
	{
		return offDate;
	}

	public void setOffDate(String offDate)
	{
		this.offDate = offDate;
	}

	public String getSchoolAvatar()
	{
		return schoolAvatar;
	}

	public void setSchoolAvatar(String schoolAvatar)
	{
		this.schoolAvatar = schoolAvatar;
	}

	/**
	 * 创建时间
	 */
	private java.util.Date createTime;

	/**
	 * 最后更新时间
	 */
	private java.util.Date updateTime;

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public Long getSchoolId()
	{
		return schoolId;
	}

	public void setSchoolId(Long schoolId)
	{
		this.schoolId = schoolId;
	}

	public String getSchoolName()
	{
		return schoolName;
	}

	public void setSchoolName(String schoolName)
	{
		this.schoolName = schoolName;
	}

	public String getXjInfo()
	{
		return xjInfo;
	}

	public void setXjInfo(String xjInfo)
	{
		this.xjInfo = xjInfo;
	}

	public String getRegisterInfo()
	{
		return registerInfo;
	}

	public void setRegisterInfo(String registerInfo)
	{
		this.registerInfo = registerInfo;
	}

	public java.util.Date getFailureTime()
	{
		return failureTime;
	}

	public void setFailureTime(java.util.Date failureTime)
	{
		this.failureTime = failureTime;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getLoginPwd()
	{
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd)
	{
		this.loginPwd = loginPwd;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}

	public String getLbs()
	{
		return lbs;
	}

	public void setLbs(String lbs)
	{
		this.lbs = lbs;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public java.util.Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime)
	{
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime)
	{
		this.updateTime = updateTime;
	}
}
