package com.kedang.fenxiao.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.google.common.collect.Maps;

/**
 * User: skyrain
 * Date: 2014/7/12
 * Time: 15:05
 */
public class JpaQueryUtils {

	public static <T> Specification<T> buildSpecification(Class<T> clazz,Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = parse(searchParams);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), clazz);
		return spec;
	}


	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if(value instanceof String){
				String valueString=String.valueOf(value);
				if(StringUtils.isBlank(valueString)){
					continue;
				}
			}else{
				if (value==null) {
					continue;
				}
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
//				throw new IllegalArgumentException(key + " is not a valid search filter name");
				continue;
			}
			String filedName = names[1];
			SearchFilter.Operator operator = SearchFilter.Operator.valueOf(names[0]);

			// 创建searchFilter


			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
}
