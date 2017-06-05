package com.kedang.fenxiao.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

/**
 * 配置文件读取
 */
public class ResourcesConfig extends
		PropertyPlaceholderConfigurer {


	public Resource getConfigResouce() {
		return configResouce;
	}

	public void setConfigResouce(Resource configResouce) {
		this.configResouce = configResouce;
	}

	private Resource configResouce;




	private Map<String, Object> configs;

	public Map<String, Object> getConfigs() {
		return configs;
	}

	public void setConfigs(Map<String, Object> configs) {
		this.configs = configs;
	}


	public Object getConfig(String key) {
		return configs.get(key);
	}

	public String getConfigString(String key) {
		return (String) configs.get(key);
	}


	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		if(configs==null)
			configs = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			configs.put(keyStr, value);
		}
	}
}
