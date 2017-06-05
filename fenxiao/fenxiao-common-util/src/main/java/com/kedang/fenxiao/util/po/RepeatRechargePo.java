package com.kedang.fenxiao.util.po;

import java.util.List;

public class RepeatRechargePo
{
	private String yysTypeId;
	private String provinceId;
	private int flowType;
	private List<String> repeatList;

	public String getYysTypeId()
	{
		return yysTypeId;
	}

	public void setYysTypeId(String yysTypeId)
	{
		this.yysTypeId = yysTypeId;
	}

	public String getProvinceId()
	{
		return provinceId;
	}

	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}

	public int getFlowType()
	{
		return flowType;
	}

	public void setFlowType(int flowType)
	{
		this.flowType = flowType;
	}

	public List<String> getRepeatList()
	{
		return repeatList;
	}

	public void setRepeatList(List<String> repeatList)
	{
		this.repeatList = repeatList;
	}

}
