package com.kedang.fenxiao.util;

import java.util.Collection;

/**
 * 
 * @author Admin
 *
 */
public class CollectionUtils
{

	/**
	 * 判断集合是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		if(null==collection || collection.size()==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断集合是否不为空
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection){
		if(null==collection || collection.size()==0){
			return false;
		}
		return true;
	}
}