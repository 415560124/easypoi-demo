package com.rhy.easypoidemo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

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
@ExcelTarget("student")
public class StudentEntity {
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
    public Integer id;
    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名" , width = 30 , isImportField = "true")
    public String name;
    /**
     * 学生性别
     */
    @Excel(name = "学生姓名" , replace = {"男_1","女_2"} , suffix = "生" , isImportField = "true")
    public int  sex;
    /**
     * 生日
     */
    @Excel(name = "出生日期" , databaseFormat = "yyyyMMdd" , format = "yyyy-MM-dd" , isImportField = "true" , width = 20)
    public LocalDate birthday;
    /**
     * 进校日期
     */
    @Excel(name = "进校日期" ,databaseFormat = "yyyyMMddHHmmss" , format = "yyyy-MM-dd HH:mm:ss" , isImportField = "true" , width = 30)
    public LocalDateTime registrationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
