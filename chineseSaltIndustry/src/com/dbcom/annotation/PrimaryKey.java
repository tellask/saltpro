package com.dbcom.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface PrimaryKey {
	// value 字符串,格式为"s1,s2,...,sn", 其中每个s为一个成员变量名,不是数据库的字段名(通过@DBField 映射到数据库的字段名).
	// 最常见的情况,一个表就只有一个字段作为主键, 比如"userId".
	// 多个字段组合作为主键, 比如"userId, serviceId".
	String value() default "";
}
