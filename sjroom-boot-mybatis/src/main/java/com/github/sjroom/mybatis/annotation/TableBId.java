package com.github.sjroom.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 表业务主键标识
 *
 * @author dream.lu
 * @since 2019-06-03
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableBId {}
