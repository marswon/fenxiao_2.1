package com.kedang.fenxiao.service.jiangsukami;

public class JiangSuKaMiResponse
{
	/**
	 * 100005	抱歉，本活动仅限电信天翼手机用户参与，感谢您的关注
	 */
	private String TSR_RESULT;//999,100005
	private String TSR_MSG;//
	private String TSR_CODE;
	private String result;

	public String getTSR_RESULT()
	{
		return TSR_RESULT;
	}

	public void setTSR_RESULT(String tSR_RESULT)
	{
		TSR_RESULT = tSR_RESULT;
	}

	public String getTSR_MSG()
	{
		return TSR_MSG;
	}

	public void setTSR_MSG(String tSR_MSG)
	{
		TSR_MSG = tSR_MSG;
	}

	public String getTSR_CODE()
	{
		return TSR_CODE;
	}

	public void setTSR_CODE(String tSR_CODE)
	{
		TSR_CODE = tSR_CODE;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

}
