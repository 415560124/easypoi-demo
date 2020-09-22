package com.rhy.easypoidemo.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.view.EasypoiSingleExcelView;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.export.ExcelExportService;

/**
 * @author Rhy
 * @title EasypoiSingleExcelView扩展类
 * @description EasypoiSingleExcelView扩展类
 * @createTime 2020年09月22日 17:06:00
 * @modifier：Rhy
 * @modification_time：2020-09-22 17:06
 */
public class EasypoiSingleExcelViewExt extends EasypoiSingleExcelView {
    public EasypoiSingleExcelViewExt() {
        super();
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        String codedFileName = "临时文件";
        Workbook workbook = null;
        if (model.containsKey(NormalExcelConstants.MAP_LIST)) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) model
                    .get(NormalExcelConstants.MAP_LIST);
            if (list.size() == 0) {
                throw new RuntimeException("MAP_LIST IS NULL");
            }
            workbook = ExcelExportUtilExt.exportExcel(
                    (ExportParams) list.get(0).get(NormalExcelConstants.PARAMS), (Class<?>) list.get(0)
                            .get(NormalExcelConstants.CLASS),
                    (Collection<?>) list.get(0).get(NormalExcelConstants.DATA_LIST));
            for (int i = 1; i < list.size(); i++) {
                new ExcelExportService().createSheet(workbook,
                        (ExportParams) list.get(i).get(NormalExcelConstants.PARAMS), (Class<?>) list
                                .get(i).get(NormalExcelConstants.CLASS),
                        (Collection<?>) list.get(i).get(NormalExcelConstants.DATA_LIST));
            }
        } else {
            workbook = ExcelExportUtil.exportExcel(
                    (ExportParams) model.get(NormalExcelConstants.PARAMS),
                    (Class<?>) model.get(NormalExcelConstants.CLASS),
                    (Collection<?>) model.get(NormalExcelConstants.DATA_LIST));
        }
        if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
            codedFileName = (String) model.get(NormalExcelConstants.FILE_NAME);
        }
        out(workbook, codedFileName, request, response);
    }
}
