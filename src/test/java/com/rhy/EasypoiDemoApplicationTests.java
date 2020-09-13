package com.rhy;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.rhy.easypoidemo.entity.CompanyEntity;
import com.rhy.easypoidemo.entity.CourseEntity;
import com.rhy.easypoidemo.entity.StudentEntity;
import com.rhy.easypoidemo.entity.TeacherEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class EasypoiDemoApplicationTests {
    //System.getProperty("user.dir") - 获取当前项目注释
    private static final String BASE_URL = System.getProperty("user.dir")+"\\file";
    /**
     * 注解导出简单示例
     * @throws IOException
     */
    @Test
    void annotationSimpleExportDemo() throws IOException {
        /**
         * 组装数据 - 这里主要看 {@link StudentEntity}类中的注解理解含义
         */
        List<StudentEntity> studentEntities = new ArrayList<>(10);
        for(int i=1;i<=10;i++){
            StudentEntity studentEntity = new StudentEntity(
                    i,
                    "学生姓名"+i,
                    i%2+1,
                    LocalDate.of(1996,i,i+10),
                    LocalDateTime.now(ZoneId.of("+8"))
            );
            studentEntities.add(studentEntity);
        }
        //导出Excel文件对象
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),StudentEntity.class,studentEntities);
        //写入文件逻辑
        exportFile(workbook,"annotation-student-demo.xls");

    }
    /**
     * 注解导出嵌套集合数据示例
     */
    @Test
    void annotationListExportDemo() throws IOException {
        /**
         * 组装数据 - 这里主要看 {@link com.rhy.easypoidemo.entity.CourseEntity}类中的注解理解含义
         */
        List<CourseEntity> courseEntities = new ArrayList<>();
        for(int i=1;i<=2;i++){
            List<StudentEntity> studentEntities = null;
            CourseEntity courseEntity = new CourseEntity()
                    .setName("课程名称"+i)
                    .setTeacherEntity(
                            new TeacherEntity()
                            .setId(i)
                            .setName("老师名称"+i)
                    )
                    .setStudentEntities(studentEntities = new ArrayList<>());
            for(int j=1;j<=5;j++){
                StudentEntity studentEntity = new StudentEntity()
                        .setName("学生姓名"+i+j)
                        .setBirthday(LocalDate.now())
                        .setRegistrationDate(LocalDateTime.now())
                        .setSex(j%2+1);
                studentEntities.add(studentEntity);
            }
            courseEntities.add(courseEntity);
        }

        //导出Excel文件对象
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("课程列表","课程"),CourseEntity.class,courseEntities);
        //写入文件逻辑
        exportFile(workbook,"annotation-course-demo.xls");

    }

    /**
     * 注解导出图片
     */
    @Test
    void annotationImgExport() throws IOException {
        List<CompanyEntity> companyEntities = new ArrayList<>();
        for(int i=1;i<=10;i++){
            companyEntities.add(
                    new CompanyEntity()
                    .setName("测试公司"+i)
                    .setImg(BASE_URL+"\\logo.jpg")
                    .setAddress("测试地址"+i)
            );
        }

        //导出Excel文件对象
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("公司列表","公司"),CompanyEntity.class,companyEntities);
        //写入文件逻辑
        exportFile(workbook,"annotation-company-demo.xls");
    }

    /**
     * 代码注解导出
     * @throws IOException
     */
    @Test
    void sourceSimpleExport() throws IOException {
        /**
         * 组装数据 - 这里主要看 {@link StudentEntity}类中的注解理解含义
         */
        //改成List<Map<String,Object>>
        List<StudentEntity> studentEntities = new ArrayList<>(10);
        for(int i=1;i<=10;i++){
            StudentEntity studentEntity = new StudentEntity(
                    i,
                    "学生姓名"+i,
                    i%2+1,
                    LocalDate.of(1996,i,i+10),
                    LocalDateTime.now(ZoneId.of("+8"))
            );
            studentEntities.add(studentEntity);
        }
        /**
         * 组装excel字段数据 - 动态追加消除列
         */
        List<ExcelExportEntity> excelExportEntities = new ArrayList<>();
        excelExportEntities.add(new ExcelExportEntity("学生姓名","name",40));
//        excelExportEntities.add(new ExcelExportEntity("学生性别","sex"));
//        excelExportEntities.add(new ExcelExportEntity("出生日期","birthday"));
//        excelExportEntities.add(new ExcelExportEntity("进校日期","registrationDate"));

        //导出Excel文件对象
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),excelExportEntities,studentEntities);
        //写入文件逻辑
        exportFile(workbook,"source-annotation-student-demo.xls");
    }
    /**
     * 写入文件
     * @param workbook
     * @param fileName
     * @throws IOException
     */
    private void exportFile(Workbook workbook,String fileName) throws IOException {
        //设置导出目录
        File file = new File(BASE_URL);
        //目录不存在则创建目录
        if(!file.exists()){
            file.mkdir();
        }
        //写入到文件流中  真正生成excel文件  可以想象成workbook对象还在内存中，现在才开始真正写入磁盘
        FileOutputStream fos = new FileOutputStream(BASE_URL+"\\"+fileName);
        //将内存excel写入磁盘文件
        workbook.write(fos);
        //写完后关闭流
        fos.close();
    }


}
