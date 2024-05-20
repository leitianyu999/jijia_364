package com.jijia.operational.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;

import java.time.LocalDate;
import java.util.Date;

public class DateConverter implements Converter<Date> {
    @Override
    public Class supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return excelContentProperty != null && excelContentProperty.getDateTimeFormatProperty() != null ? DateUtils.parseDate(cellData.getStringValue(), excelContentProperty.getDateTimeFormatProperty().getFormat()) : DateUtils.parseDate(cellData.getStringValue(), (String)null);
    }

    @Override
    public CellData convertToExcelData(Date date, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return excelContentProperty != null && excelContentProperty.getDateTimeFormatProperty() != null ? new CellData(DateUtils.format(date, excelContentProperty.getDateTimeFormatProperty().getFormat())) : new CellData(DateUtils.format(date, DateUtils.DATE_FORMAT_10));
    }
}
