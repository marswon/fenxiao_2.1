package com.kedang.fenxiao.util.po;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: skyrain
 * Date: 14-4-16
 * Time: 下午2:15
 */
public class ResultDo {
	private Integer resultCode=1;
	private String resultMsg;
	private Object content;
	public final static Integer success_code=1;
	public final static Integer failed_code=-1;
	public final static Integer PARAM_EMPTY_CODE=-2;//参数值为空
	public final static Integer FILE_UPLOAD_FAILURE=-3;//文件上传失败
	public final static Integer NOT_LOGIN=-100;//未登录
	public final static Integer LOGIN_FAILURE=-101;//登录失败
	public final static Integer PERMISSION_INSUFFICIENT=-102;//权限不足
	public final static Integer SERVICE_EXCEPTION=-110;//业务层异常
	public final static Integer UNKNOW_EXCEPTION=-500;//系统内部错误

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public ResultDo(Integer resultCode,String resultMsg,Object content){
		this.resultCode=resultCode;
		this.resultMsg=resultMsg;
		this.content=content;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("resultCode", resultCode)
				.append("resultMsg", resultMsg)
				.append("content", content)
				.toString();
	}
	
}
