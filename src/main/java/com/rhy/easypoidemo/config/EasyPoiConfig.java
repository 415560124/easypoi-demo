package com.rhy.easypoidemo.config;

import cn.afterturn.easypoi.excel.export.base.ExportCommonService;
import com.rhy.easypoidemo.service.ExcelExportServiceExtImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Rhy
 * @title easypoi配置
 * @description easypoi配置
 * @createTime 2020年09月22日 16:28:00
 * @modifier：Rhy
 * @modification_time：2020-09-22 16:28
 */
@Configuration
public class EasyPoiConfig {
    @Bean
    @Primary
    public ExportCommonService exportCommonService(){
        return new ExcelExportServiceExtImpl();
    }
}
