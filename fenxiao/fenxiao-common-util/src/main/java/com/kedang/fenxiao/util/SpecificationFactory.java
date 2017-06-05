package com.kedang.fenxiao.util;

import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

/**
 *	构建查询规范的工厂
 */
public class SpecificationFactory {
	
	public static <T> Specification<T> buildSpecification(Class<T> clazz,
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), clazz);
		return spec;
	}
}
