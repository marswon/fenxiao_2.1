package com.kedang.fenxiao.util.po;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BackupAuthAuthenticate
{
	private Long userId;//用户编号
	private String name;//真实姓名
	private String idCode;//身份证号
	private Integer sex;//性别
	private String homeTown;//籍贯
	private Long schoolId;//学校编号
	private String schoolName;//学校名称
	private Long facultyId;//所属院系
	private String facultyName;//院系名称
	private String year;//入学年份
	private Integer totalYear;//年制
	private String lbs;//lbs
	@JsonIgnore
	private CommonsMultipartFile idPersonPics;//手持身份证正面照
	@JsonIgnore
	private List<CommonsMultipartFile> xszPersonPics;//手持学生证正面照

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIdCode()
	{
		return idCode;
	}

	public void setIdCode(String idCode)
	{
		this.idCode = idCode;
	}

	public Integer getSex()
	{
		return sex;
	}

	public void setSex(Integer sex)
	{
		this.sex = sex;
	}

	public Long getSchoolId()
	{
		return schoolId;
	}

	public void setSchoolId(Long schoolId)
	{
		this.schoolId = schoolId;
	}

	public Long getFacultyId()
	{
		return facultyId;
	}

	public void setFacultyId(Long facultyId)
	{
		this.facultyId = facultyId;
	}

	public String getYear()
	{
		return year;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

	public Integer getTotalYear()
	{
		return totalYear;
	}

	public void setTotalYear(Integer totalYear)
	{
		this.totalYear = totalYear;
	}

	public CommonsMultipartFile getIdPersonPics()
	{
		return idPersonPics;
	}

	public void setIdPersonPics(CommonsMultipartFile idPersonPics)
	{
		this.idPersonPics = idPersonPics;
	}

	public List<CommonsMultipartFile> getXszPersonPics()
	{
		return xszPersonPics;
	}

	public void setXszPersonPics(List<CommonsMultipartFile> xszPersonPics)
	{
		this.xszPersonPics = xszPersonPics;
	}

	public String getHomeTown()
	{
		return homeTown;
	}

	public void setHomeTown(String homeTown)
	{
		this.homeTown = homeTown;
	}

	public String getSchoolName()
	{
		return schoolName;
	}

	public void setSchoolName(String schoolName)
	{
		this.schoolName = schoolName;
	}

	public String getFacultyName()
	{
		return facultyName;
	}

	public void setFacultyName(String facultyName)
	{
		this.facultyName = facultyName;
	}

	public String getLbs()
	{
		return lbs;
	}

	public void setLbs(String lbs)
	{
		this.lbs = lbs;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getUserId())
				.append("Name", getName()).append("IdCode", getIdCode()).append("Sex", getSex())
				.append("SchoolId", getSchoolId()).append("FacultyId", getFacultyId()).append("Year", getYear()).append("Lbs",getLbs())
				.append("HomeTown", getHomeTown()).toString();
	}
}
