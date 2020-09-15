package com.rhy.easypoidemo.controller;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.rhy.easypoidemo.entity.StudentEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author Herion_Rhy
 * @Date 2020/9/13 22:22
 * @ModifyDate 2020/9/13 22:22
 * @Version
 */
@RestController
public class TestController {
    /**
     * http://127.0.0.1:8080/download
     * @param modelMap
     * @param request
     * @param response
     */
    @GetMapping("download")
    public void testDownload(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
        /**
         * 组装数据
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
            studentEntity.put("name","学生姓名"+i);
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
         * 组装数据结束 - 相当于我们从dao层查询数据后拼装到map中 - 数据格式查询 学生信息列表.png
         */
        /**
         * 组装excel字段数据 - 动态追加消除列
         */
        List<ExcelExportEntity> excelExportEntities = new ArrayList<>();
        //学生姓名列
        ExcelExportEntity excelExportNameEntity = new ExcelExportEntity("学生姓名","name",25);
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
        //写入成绩相关标题项
        excelExportEntityScore.setList(excelExportEntityChildrens);
        excelExportEntityChildrens.add(new ExcelExportEntity("学期","semester"));
        excelExportEntityChildrens.add(new ExcelExportEntity("语文","chinese"));
        excelExportEntityChildrens.add(new ExcelExportEntity("数学","math"));
        excelExportEntityChildrens.add(new ExcelExportEntity("英语","english"));

        ExportParams params = new ExportParams("计算机一班学生","学生");
        params.setFreezeCol(2);
        modelMap.put(MapExcelConstants.MAP_LIST, studentEntities);
        modelMap.put(MapExcelConstants.ENTITY_LIST, excelExportEntities);
        modelMap.put(MapExcelConstants.PARAMS, params);
        modelMap.put(MapExcelConstants.FILE_NAME, "EasypoiMapExcelViewTest");
        PoiBaseView.render(modelMap, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);
    }
}
