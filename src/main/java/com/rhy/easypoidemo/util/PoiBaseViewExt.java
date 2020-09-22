package com.rhy.easypoidemo.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import cn.afterturn.easypoi.entity.vo.BigExcelConstants;
import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.MapExcelGraphConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;

/**
 * @author Rhy
 * @title PoiBaseView扩展
 * @description PoiBaseView扩展
 * @createTime 2020年09月22日 16:56:00
 * @modifier：Rhy
 * @modification_time：2020-09-22 16:56
 */
public class PoiBaseViewExt {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiBaseViewExt.class);

    public static void render(Map<String, Object> model, HttpServletRequest request,HttpServletResponse response, String viewName) {
        PoiBaseView view = null;
        //如果是注解形式导出拦截执行扩展类
        if (NormalExcelConstants.EASYPOI_EXCEL_VIEW.equals(viewName)) {
            view = new EasypoiSingleExcelViewExt();
        }else{
            PoiBaseView.render(model,request,response,viewName);
        }
        try {
            ((EasypoiSingleExcelViewExt)view).renderMergedOutputModel(model, request, response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
