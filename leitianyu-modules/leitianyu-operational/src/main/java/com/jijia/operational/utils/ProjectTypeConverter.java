package com.jijia.operational.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.jijia.operational.utils.constants.DeskConstants;
import com.jijia.operational.utils.enums.DeskInsertType;

import java.util.Objects;


public class ProjectTypeConverter implements Converter<Integer> {
    //在java中性别是用 0 1 来标识的  所以是int
    @Override
    public Class supportJavaTypeKey() {return Integer.class;}
    // 在excel中是男女 所以是string
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {return CellDataTypeEnum.STRING;}
    //将excel的数据类型转为java数据类型
    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        return Objects.requireNonNull(DeskInsertType.getEnum(stringValue)).getStatus();


    }

    @Override
    public CellData convertToExcelData(Integer s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData<>(Objects.requireNonNull(DeskInsertType.getEnum(s)).getMsg());
    }

    //将java的数据类型转为excel数据类型
}
