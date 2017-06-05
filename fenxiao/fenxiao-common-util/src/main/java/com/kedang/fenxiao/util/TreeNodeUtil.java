package com.kedang.fenxiao.util;

import com.kedang.fenxiao.util.po.JsonTreeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gegongxian on 17/6/1.
 */
public class TreeNodeUtil
{
	/**
	 * @Title: getfatherNode
	 * @Description 方法描述: 父节点
	 * @return 返回类型：List<JsonTreeData>
	 * @throws
	 * @date 最后修改时间：2015年6月9日 下午6:39:26
	 */
	public final static List<JsonTreeData> getfatherNode(List<JsonTreeData> treeDataList)
	{
		List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
		for (JsonTreeData jsonTreeData : treeDataList)
		{
			if (jsonTreeData.getPid().equals("0"))
			{
				//获取父节点下的子节点
				jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
				jsonTreeData.setState("open");
				newTreeDataList.add(jsonTreeData);
			}
		}
		return newTreeDataList;
	}

	/**
	 * @Title: getChildrenNode
	 * @Description 方法描述: 子节点
	 * @return 返回类型：List<JsonTreeData>
	 * @throws
	 * @date 最后修改时间：2015年6月9日 下午6:39:50
	 */
	private final static List<JsonTreeData> getChildrenNode(String pid, List<JsonTreeData> treeDataList)
	{
		List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
		for (JsonTreeData jsonTreeData : treeDataList)
		{
			if (jsonTreeData.getPid() == null)
				continue;
			//这是一个子节点
			if (jsonTreeData.getPid().equals(pid))
			{
				//递归获取子节点下的子节点
				jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
				newTreeDataList.add(jsonTreeData);
			}
		}
		return newTreeDataList;
	}
}
