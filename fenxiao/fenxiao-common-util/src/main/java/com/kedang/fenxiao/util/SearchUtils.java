package com.kedang.fenxiao.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.utils.Collections3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SearchUtils {
	public enum Operator {
		EQ, NEQ, LIKE, GT, LT, GTE, LTE,IN,ISNULL,NOTNULL
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchUtils(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * 解析传入的map查询参数并过滤空值
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 * @param searchParams
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, SearchUtils> parse(Map<String, Object> searchParams) {
		Map<String, SearchUtils> filters = Maps.newHashMap();

		for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
				if(value instanceof String){
					if(StringUtils.isBlank(String.valueOf(value))){
						continue;
					}
				}else if(value instanceof Collection){
					if(Collections3.isEmpty((Collection)value)){
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
			String fieldName = names[1];
			SearchUtils.Operator operator = SearchUtils.Operator.valueOf(names[0]);

			// 创建searchFilter


			SearchUtils filter = new SearchUtils(fieldName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
	/**
	 * 封装spring data jpa查询规范
	 * @param clazz
	 * @param searchParams
	 * @return
	 */
	public static <T> Specification<T> buildSpec(Class<T> clazz, Map<String, Object> searchParams) {
		final Map<String, SearchUtils> filters = parse(searchParams);
		return new Specification<T>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				if (Collections3.isNotEmpty(filters.values())) {

					List<Predicate> predicates = Lists.newArrayList();
					for (SearchUtils filter : filters.values()) {
						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						// logic operator
						switch (filter.operator) {
						case EQ:
							predicates.add(builder.equal(expression, filter.value));
							break;
						case NEQ:
							predicates.add(builder.notEqual(expression, filter.value));
							break;
						case LIKE:
							predicates.add(builder.like(expression, filter.value + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case IN:
							predicates.add(builder.in(expression).value(filter.value));
							break;
						case ISNULL:
							predicates.add(builder.isNull(expression));
							break;
						case NOTNULL:
							predicates.add(builder.isNotNull(expression));
							break;
						}
					}

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}
				return builder.conjunction();
			}
		};
	}
}
