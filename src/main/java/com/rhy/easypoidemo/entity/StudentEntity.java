package com.rhy.easypoidemo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Rhy
 * @title 学生实体类
 * @description 学生实体类
 * http://doc.wupaas.com/docs/easypoi/easypoi-1c0u96flii98v 注解每一项含义
 * @createTime 2020年09月12日 13:27:00
 * @modifier：Rhy
 * @modification_time：2020-09-12 13:27
 */
@ExcelTarget("StudentEntity")
@Getter
@Setter
@Accessors(chain=true) // 可以链式调用 setter
public class StudentEntity {
    public StudentEntity() {
    }

    public StudentEntity(Integer id, String name, int sex, LocalDate birthday, LocalDateTime registrationDate) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.registrationDate = registrationDate;
    }

    /**
     * id
     */
    private Integer id;
    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名" , width = 30 , isImportField = "true")
    private String name;
    /**
     * 学生性别
     */
    @Excel(name = "学生性别" , replace = {"男_1","女_2"} , suffix = "生" , isImportField = "true_st")
    private int  sex;
    /**
     * 生日
     */
    @Excel(name = "出生日期" , databaseFormat = "yyyyMMdd" , format = "yyyy-MM-dd" , isImportField = "true_st" , width = 20)
    private LocalDate birthday;
    /**
     * 进校日期
     */
    @Excel(name = "进校日期" ,databaseFormat = "yyyyMMddHHmmss" , format = "yyyy-MM-dd HH:mm:ss" , isImportField = "true_st" , width = 30)
    private LocalDateTime registrationDate;

}
