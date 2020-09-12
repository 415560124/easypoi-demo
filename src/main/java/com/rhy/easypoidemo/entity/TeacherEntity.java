package com.rhy.easypoidemo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Rhy
 * @title 老师实体类
 * @description 老师实体类
 * @createTime 2020年09月12日 13:14:00
 * @modifier：Rhy
 * @modification_time：2020-09-12 13:14
 */
@ExcelTarget("TeacherEntity")
@Getter
@Setter
@Accessors(chain=true) // 可以链式调用 setter
public class TeacherEntity implements Serializable {
    @Excel(name = "老师编号" , orderNum = "2",needMerge = true)
    private Integer id;
    @Excel(name = "主讲老师_teacherEntity,代课老师_absent", orderNum = "1",needMerge = true,isImportField = "true_major,true_absent")
    private String name;
}
