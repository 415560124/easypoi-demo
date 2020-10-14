package com.rhy;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.rhy.easypoidemo.config.ExcelColumnNum;
import com.rhy.easypoidemo.config.ExcelExportStatisticStyler;
import com.rhy.easypoidemo.entity.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
        ExportParams exportParams = new ExportParams("计算机一班学生","学生");
        //导出Excel文件对象
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,StudentEntity.class,studentEntities);
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
        List<Map<String,Object>> studentEntities = new ArrayList<>(10);
        //行数据
        Map<String,Object> studentEntity = null;
        //学生成绩数据
        List<Map<String,Object>> studentScoreEntities = null;
        //学生成绩行数据
        Map<String,Object> studentScoreEntity = null;
        for(int i=1;i<=10;i++){
            studentEntity = new HashMap<>();
            studentEntities.add(studentEntity);
            studentEntity.put("name","111111111111111111111111111111111111111111111111111111222222222222222222222222222222222222233333333333333333333333333333333333333333"+i);
            studentEntity.put("sex",i%2+1);
            studentEntity.put("birthday",LocalDate.of(1996,i,i+10));
            studentEntity.put("registrationDate",LocalDateTime.now(ZoneId.of("+8")));
            //学生分数
            studentScoreEntities = new ArrayList<>();
            studentEntity.put("scores",studentScoreEntities);
            studentScoreEntity = new HashMap<>();
            studentScoreEntities.add(studentScoreEntity);
            studentScoreEntity.put("semester","上学期");
            studentScoreEntity.put("chinese",96+i);
            studentScoreEntity.put("math",60+i);
            studentScoreEntity.put("english",76+i);
            studentScoreEntity = new HashMap<>();
            studentScoreEntities.add(studentScoreEntity);
            studentScoreEntity.put("semester","下学期");
            studentScoreEntity.put("chinese",95+i);
            studentScoreEntity.put("math",79+i);
            studentScoreEntity.put("english",66+i);
        }
        /**
         * 组装excel字段数据 - 动态追加消除列
         */
        List<ExcelExportEntity> excelExportEntities = new ArrayList<>();
        //学生姓名列
        ExcelExportEntity excelExportNameEntity = new ExcelExportEntity("学生姓名","name",3);
        //需要合并
        excelExportNameEntity.setNeedMerge(true);
        excelExportEntities.add(excelExportNameEntity);
        //学生性别列
        ExcelExportEntity excelExportSexEntity = new ExcelExportEntity("学生性别","sex");
        //需要合并
        excelExportSexEntity.setNeedMerge(true);
        //转换枚举
        excelExportSexEntity.setReplace(new String[]{"男_1","女_2"});
        //字段后缀
        excelExportSexEntity.setSuffix("生");
        excelExportEntities.add(excelExportSexEntity);
//        excelExportEntities.add(new ExcelExportEntity("出生日期","birthday",20));
//        excelExportEntities.add(new ExcelExportEntity("进校日期","registrationDate",40));
        //学生成绩列集合
        ExcelExportEntity excelExportEntityScore = new ExcelExportEntity("学生成绩","scores");
        excelExportEntities.add(excelExportEntityScore);
        //成绩列标题项
        List<ExcelExportEntity> excelExportEntityChildrens = new ArrayList<>();
        excelExportEntityChildrens.add(new ExcelExportEntity("学期","semester"));
        excelExportEntityChildrens.add(new ExcelExportEntity("语文","chinese"));
        excelExportEntityChildrens.add(new ExcelExportEntity("数学","math"));
        excelExportEntityChildrens.add(new ExcelExportEntity("英语","english"));
        //写入成绩相关标题项
        excelExportEntityScore.setList(excelExportEntityChildrens);
        ExportParams exportParams = new ExportParams("计算机一班学生","学生");
        exportParams.setStyle(ExcelExportStatisticStyler.class);
        //设置字段数
        ExcelColumnNum.set(10);
        //导出Excel文件对象
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,excelExportEntities,studentEntities);

        //设置每个表格的列自动适应
            //获得excel表格数
            int sheetNum = workbook.getNumberOfSheets();
            //遍历每个表格
            for(int i=0;i<sheetNum;i++){
                Sheet sheet = workbook.getSheetAt(i);
                setSizeColumn(sheet, 6);
                //获得行数
                int rowNum = sheet.getLastRowNum();
                //遍历行
                for(int j=0;j<rowNum;j++){
                    Row row = sheet.getRow(j);
                    //获得字段数
                    int cellNum = row.getLastCellNum();
                    //行高
                    int rowHeight = 0;
                    //遍历字段
                    for(int x=0;x<cellNum;x++){
                        Cell cell = row.getCell(x);
                        String cellValue = cell.getStringCellValue();
                    }
                }
            }

        //写入文件逻辑
        exportFile(workbook,"source-annotation-student-demo.xls");
    }

    // 自适应宽度(中文支持)
    private void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    int length = currentCell.getStringCellValue().getBytes().length;
                    if (columnWidth < length) {
                        columnWidth = length;
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
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
    @Test
    public void collectionBug() throws IOException {
        CourseEntityCollection courseEntityCollection = new CourseEntityCollection();
        courseEntityCollection.setName("name");
        List<CourseEntityCollection> courseEntityCollections = new ArrayList<>();
        courseEntityCollections.add(new CourseEntityCollection().setName("name"));
        courseEntityCollection.setStudentEntities(courseEntityCollections);

        List<CourseEntityCollection> companyEntities = new ArrayList<>();
        companyEntities.add(courseEntityCollection);
        //导出Excel文件对象
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("公司列表","公司"),CourseEntityCollection.class,companyEntities);
        //写入文件逻辑
        exportFile(workbook,"collectionBug.xls");
    }

}
