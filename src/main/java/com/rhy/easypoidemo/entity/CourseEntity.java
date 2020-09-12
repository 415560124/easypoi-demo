package com.rhy.easypoidemo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Rhy
 * @title 课程实体类
 * @description 课程实体类
 * @createTime 2020年09月12日 14:35:00
 * @modifier：Rhy
 * @modification_time：2020-09-12 14:35
 */
@ExcelTarget("CourseEntity")
@Getter
@Setter
@Accessors(chain=true) // 可以链式调用 setter
public class CourseEntity {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 课程名称
     */
    @Excel(name = "课程名称",orderNum = "1",width = 25,needMerge = true)
    private String name;
    /**
     * 老师对象
     */
    @ExcelEntity(id = "absent")
    private TeacherEntity teacherEntity;
    /**
     * 学生集合
     */
    @ExcelCollection(name = "学生" , orderNum = "4")
    List<StudentEntity> studentEntities;

}
