package com.rhy.easypoidemo.service;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import cn.afterturn.easypoi.excel.export.base.ExportCommonService;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.afterturn.easypoi.exception.excel.enums.ExcelExportEnum;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import cn.afterturn.easypoi.util.PoiReflectorUtil;
import com.rhy.easypoidemo.entity.ExcelCollectionDeep;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author Rhy
 * @title 导出扩展实现
 * @description 导出扩展实现
 * @createTime 2020年09月22日 16:14:00
 * @modifier：Rhy
 * @modification_time：2020-09-22 16:14
 */
public class ExcelExportServiceExtImpl extends ExcelExportService {
    private Map<String,Integer> deepNums = new HashMap<>();
    @Override
    public void getAllExcelField(String[] exclusions, String targetId, Field[] fields, List<ExcelExportEntity> excelParams, Class<?> pojoClass, List<Method> getMethods, ExcelEntity excelGroup) throws Exception {
        List<String> exclusionsList = exclusions != null ? Arrays.asList(exclusions) : null;
        ExcelExportEntity excelEntity;
        // 遍历整个filed
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 先判断是不是collection,在判断是不是java自带对象,之后就是我们自己的对象了
            if (PoiPublicUtil.isNotUserExcelUserThis(exclusionsList, field, targetId)) {
                continue;
            }
            // 首先判断Excel 可能一下特殊数据用户回自定义处理
            if (field.getAnnotation(Excel.class) != null) {
                Excel excel = field.getAnnotation(Excel.class);
                String name = PoiPublicUtil.getValueByTargetId(excel.name(), targetId, null);
                if (StringUtils.isNotBlank(name)) {
                    excelParams.add(createExcelExportEntity(field, targetId, pojoClass, getMethods, excelGroup));
                }
            } else if (PoiPublicUtil.isCollection(field.getType())) {
                ExcelCollection excel = field.getAnnotation(ExcelCollection.class);
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                Class<?> clz = (Class<?>) pt.getActualTypeArguments()[0];
                List<ExcelExportEntity> list = new ArrayList<ExcelExportEntity>();
                if(field.isAnnotationPresent(ExcelCollectionDeep.class)){
                    int deepNum = deepNums.get(field.getName()) == null ? 1 : deepNums.get(field.getName());
                    if(deepNum <= field.getAnnotation(ExcelCollectionDeep.class).num()){
                        deepNums.put(field.getName(),++deepNum);
                        //判断注解深度
                        getAllExcelField(exclusions,
                                StringUtils.isNotEmpty(excel.id()) ? excel.id() : targetId,
                                PoiPublicUtil.getClassFields(clz), list, clz, null, null);
                    }else{
                        deepNums.remove(field.getName());
                    }
                }else{
                    //判断注解深度
                    getAllExcelField(exclusions,
                            StringUtils.isNotEmpty(excel.id()) ? excel.id() : targetId,
                            PoiPublicUtil.getClassFields(clz), list, clz, null, null);
                }
                excelEntity = new ExcelExportEntity();
                excelEntity.setName(PoiPublicUtil.getValueByTargetId(excel.name(), targetId, null));
                if (i18nHandler != null) {
                    excelEntity.setName(i18nHandler.getLocaleName(excelEntity.getName()));
                }
                excelEntity.setOrderNum(Integer
                        .valueOf(PoiPublicUtil.getValueByTargetId(excel.orderNum(), targetId, "0")));
                excelEntity
                        .setMethod(PoiReflectorUtil.fromCache(pojoClass).getGetMethod(field.getName()));
                excelEntity.setList(list);
                excelParams.add(excelEntity);
            } else {
                List<Method> newMethods = new ArrayList<Method>();
                if (getMethods != null) {
                    newMethods.addAll(getMethods);
                }
                newMethods.add(PoiReflectorUtil.fromCache(pojoClass).getGetMethod(field.getName()));
                ExcelEntity excel = field.getAnnotation(ExcelEntity.class);
                if (excel.show() && StringUtils.isEmpty(excel.name())) {
                    throw new ExcelExportException("if use ExcelEntity ,name mus has value ,data: " + ReflectionToStringBuilder.toString(excel), ExcelExportEnum.PARAMETER_ERROR);
                }
                getAllExcelField(exclusions,
                        StringUtils.isNotEmpty(excel.id()) ? excel.id() : targetId,
                        PoiPublicUtil.getClassFields(field.getType()), excelParams, field.getType(),
                        newMethods, excel.show() ? excel : null);
            }
        }
    }

    /**
     * 创建导出实体对象
     */
    private ExcelExportEntity createExcelExportEntity(Field field, String targetId,
                                                      Class<?> pojoClass,
                                                      List<Method> getMethods, ExcelEntity excelGroup) throws Exception {
        Excel excel = field.getAnnotation(Excel.class);
        ExcelExportEntity excelEntity = new ExcelExportEntity();
        excelEntity.setType(excel.type());
        getExcelField(targetId, field, excelEntity, excel, pojoClass, excelGroup);
        if (getMethods != null) {
            List<Method> newMethods = new ArrayList<Method>();
            newMethods.addAll(getMethods);
            newMethods.add(excelEntity.getMethod());
            excelEntity.setMethods(newMethods);
        }
        return excelEntity;
    }

    /**
     * 注解到导出对象的转换
     */
    private void getExcelField(String targetId, Field field, ExcelExportEntity excelEntity,
                               Excel excel, Class<?> pojoClass, ExcelEntity excelGroup) throws Exception {
        excelEntity.setName(PoiPublicUtil.getValueByTargetId(excel.name(), targetId, null));
        excelEntity.setWidth(excel.width());
        excelEntity.setHeight(excel.height());
        excelEntity.setNeedMerge(excel.needMerge());
        excelEntity.setMergeVertical(excel.mergeVertical());
        excelEntity.setMergeRely(excel.mergeRely());
        excelEntity.setReplace(excel.replace());
        excelEntity.setOrderNum(
                Integer.valueOf(PoiPublicUtil.getValueByTargetId(excel.orderNum(), targetId, "0")));
        excelEntity.setWrap(excel.isWrap());
        excelEntity.setExportImageType(excel.imageType());
        excelEntity.setSuffix(excel.suffix());
        excelEntity.setDatabaseFormat(excel.databaseFormat());
        excelEntity.setFormat(
                StringUtils.isNotEmpty(excel.exportFormat()) ? excel.exportFormat() : excel.format());
        excelEntity.setStatistics(excel.isStatistics());
        excelEntity.setHyperlink(excel.isHyperlink());
        excelEntity.setMethod(PoiReflectorUtil.fromCache(pojoClass).getGetMethod(field.getName()));
        excelEntity.setNumFormat(excel.numFormat());
        excelEntity.setColumnHidden(excel.isColumnHidden());
        excelEntity.setDict(excel.dict());
        excelEntity.setEnumExportField(excel.enumExportField());
        excelEntity.setTimezone(excel.timezone());
        if (excelGroup != null) {
            excelEntity.setGroupName(PoiPublicUtil.getValueByTargetId(excelGroup.name(), targetId, null));
        } else {
            excelEntity.setGroupName(excel.groupName());
        }
        if (i18nHandler != null) {
            excelEntity.setName(i18nHandler.getLocaleName(excelEntity.getName()));
            excelEntity.setGroupName(i18nHandler.getLocaleName(excelEntity.getGroupName()));
        }
    }
}
