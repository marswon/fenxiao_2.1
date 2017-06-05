package com.kedang.fenxiao.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: skyrain
 * Date: 2014/7/30
 * Time: 18:56
 */
public class ExBeanUtils  extends BeanUtils {
	private static Logger logger = LoggerFactory.getLogger(ExBeanUtils.class);
	static {
//		ConvertUtils.register(new DateConvert(), java.util.Date.class);
//		ConvertUtils.register(new DateConvert(), java.sql.Date.class);
//		ConvertUtils.register(new DateConvert(), java.sql.Timestamp.class);
		BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

	}

	public static void copyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		}
	}

	public static void copyIgnoreNulls(Object target,Object source){
		BeanInfo beanInfo=null;

		try {
			beanInfo = Introspector.getBeanInfo(source.getClass());
		} catch (IntrospectionException e) {
//			logger.debug("------------>bean加载失败！");
			return;
		}
		PropertyDescriptor[] propertieDescritors = beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor propertyDescriptor : propertieDescritors){
			logger.debug("------------>当前操作属性："+propertyDescriptor.getName());
			Method readMethod = propertyDescriptor.getReadMethod();
			Method writeMethod = propertyDescriptor.getWriteMethod();
			if(writeMethod != null&&readMethod != null){
				if(!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers()))
					readMethod.setAccessible(true);
				Object value=null;
				try {
					value = readMethod.invoke(source, new Object[0]);
				} catch (Exception e) {
					logger.debug("------------>getter方法读取失败");
					continue;
				}
				if(!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers()))
					writeMethod.setAccessible(true);
				if (value!=null&&!"".equals(value.toString())) {
					//关键在此处的判断，如果为空，不再copy
					try {
						writeMethod.invoke(target, new Object[] {value});
						logger.debug("------------>属性拷贝："+value);
					} catch (Exception e) {
						logger.debug("------------>setter方法写入失败");
						continue;
					}
				}else {
					logger.debug("------------>属性为空，不再拷贝");
				}
			}
		}
	}
}
