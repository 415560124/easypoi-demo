package com.rhy.easypoidemo.config;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.apache.poi.ss.usermodel.*;

/**
 * Excel 自定义styler 的例子
 * @author JueYue
 *   2015年3月29日 下午9:04:41
 */
public class ExcelExportStatisticStyler extends ExcelExportStylerDefaultImpl {

    private CellStyle borderCellStyle;

    public ExcelExportStatisticStyler(Workbook workbook) {
        super(workbook);
        createNumberCellStyler(true);
    }

    private void createNumberCellStyler(boolean isWarp) {
        borderCellStyle = workbook.createCellStyle();
        borderCellStyle.setBorderLeft(BorderStyle.THIN);
        borderCellStyle.setBorderRight(BorderStyle.THIN);
        borderCellStyle.setBorderBottom(BorderStyle.THIN);
        borderCellStyle.setBorderTop(BorderStyle.THIN);
        borderCellStyle.setAlignment(HorizontalAlignment.CENTER);
        borderCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        borderCellStyle.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            borderCellStyle.setWrapText(true);
        }
    }

    /**
     * 字段样式
     * @param noneStyler
     * @param entity
     * @return
     */
    @Override
    public CellStyle getStyles(boolean noneStyler, ExcelExportEntity entity) {
        return borderCellStyle;
    }

    /**
     * 标题样式
     * @param color
     * @return
     */
    @Override
    public CellStyle getTitleStyle(short color) {
        return borderCellStyle;
    }

    /**
     * 头部样式
     * @param color
     * @return
     */
//    @Override
//    public CellStyle getHeaderStyle(short color) {
//        return borderCellStyle;
//    }
}
