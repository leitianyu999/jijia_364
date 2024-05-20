package com.jijia.operational.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;


public class EntrustStatusConverter implements Converter<Integer> {
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
        if ("正常".equals(stringValue)) {
            return 0;
        }else if ("异常".equals(stringValue)){
            return 1;
        }

        return null;


    }

    @Override
    public CellData convertToExcelData(Integer s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (s.equals(0)) {
            return new CellData<>("正常");
        }else if (s.equals(1)){
            return new CellData<>("异常");
        }

        return null;
    }

    //将java的数据类型转为excel数据类型
}
