package com.kedang.fenxiao.util.po;

import java.util.List;

/**
 * Created by gegongxian on 17/6/1.
 */
public class JsonTreeData {
    public String id;       //json id
    public String pid;      //
    public String text;     //json 显示文本
    public String state;    //json 'open','closed'
    public String checked;	//是否选中
    
    public String getChecked()
	{
		return checked;
	}
	public void setChecked(String checked)
	{
		this.checked = checked;
	}
	public List<JsonTreeData> children;       //

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public List<JsonTreeData> getChildren() {
        return children;
    }
    public void setChildren(List<JsonTreeData> children) {
        this.children = children;
    }
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
}
