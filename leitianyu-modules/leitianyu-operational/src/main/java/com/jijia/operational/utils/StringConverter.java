package com.jijia.operational.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;


public class StringConverter implements Converter<String> {
    //在java中性别是用 0 1 来标识的  所以是int
    @Override
    public Class supportJavaTypeKey() {return String.class;}
    // 在excel中是男女 所以是string
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {return CellDataTypeEnum.STRING;}
    //将excel的数据类型转为java数据类型
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        if ("是".equals(stringValue)) {
            return "1";
        }else if ("否".equals(stringValue)){
            return "0";
        }

        return null;


    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if ("1".equals(s)) {
            return new CellData("是");
        }else if ("0".equals(s)){
            return new CellData("否");
        }

        return null;
    }

    //将java的数据类型转为excel数据类型
}
