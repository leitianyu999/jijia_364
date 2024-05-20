package com.jijia.operational.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;


public class CalibrationMsgStatusConverter implements Converter<Integer> {
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
        switch (stringValue) {
            case "未出":
                return 0;
            case "已出未盖章":
                return 1;
            case "已盖章":
                return 2;
            default:
                return null;
        }
    }

    @Override
    public CellData convertToExcelData(Integer s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        switch (s) {
            case 0:
                return new CellData<>("未出");
            case 1:
                return new CellData<>("已出未盖章");
            case 2:
                return new CellData<>("已盖章");
            default:
                return null;
        }
    }

    //将java的数据类型转为excel数据类型
}
