package com.rhy.easypoidemo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Rhy
 * @title 公司实体类
 * @description 公司实体类
 * @createTime 2020年09月12日 15:38:00
 * @modifier：Rhy
 * @modification_time：2020-09-12 15:38
 */

@ExcelTarget(value = "CourseEntity")
@Getter
@Setter
@Accessors(chain=true) // 可以链式调用 setter
public class CompanyEntity {
    @Excel(name = "公司名" , width = 30)
    private String name;
    @Excel(name = "公司Logo" , type = 2 , imageType = 1 , width = 40 , height = 80)
    private String img;
    @Excel(name = "公司地址" , width = 50)
    private String address;
}
