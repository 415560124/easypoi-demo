package com.rhy.easypoidemo.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author Rhy
 * @title 递归深度
 * @description 递归深度
 * @createTime 2020年09月22日 16:07:00
 * @modifier：Rhy
 * @modification_time：2020-09-22 16:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCollectionDeep {


    /**
     * 递归深度
     */
    public int num() default 1;

}
