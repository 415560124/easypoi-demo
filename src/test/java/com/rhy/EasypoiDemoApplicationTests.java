package com.rhy;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.rhy.easypoidemo.entity.StudentEntity;
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

    @Test
    void contextLoads() throws IOException {
        //组装数据
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
        //设置导出目录
        File file = new File("D:\\ideaProject\\easypoi-demo\\file");
        //目录不存在则创建目录
        if(!file.exists()){
            file.mkdir();
        }
        //写入到文件流中  真正生成excel文件  可以想象成workbook对象还在内存中，现在才开始真正写入磁盘
        FileOutputStream fos = new FileOutputStream("D:\\ideaProject\\easypoi-demo\\file\\annotation-student-demo.xls");
        //将内存excel写入磁盘文件
        workbook.write(fos);
        //写完后关闭流
        fos.close();
    }

}
