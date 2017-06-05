package com.kedang.fenxiao.util.po;

public class AuthChsiSwitch
{
	private Short status = 0;
	public static AuthChsiSwitch authChsiSwitchInstance;

	public AuthChsiSwitch()
	{
	}

	public static AuthChsiSwitch getInstance()//步骤三  
	{
		if (authChsiSwitchInstance == null)
		{
			authChsiSwitchInstance = new AuthChsiSwitch();
		}
		return authChsiSwitchInstance;
	}

	public Short getStatus()
	{
		return status;
	}

	public void setStatus(Short status)
	{
		this.status = status;
	}
}
