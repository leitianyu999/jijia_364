package com.jijia.operational.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.jijia.operational.utils.constants.DeskConstants;


public class ProgramTypeConverter implements Converter<Integer> {
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
            case DeskConstants.Desk_YUANCAI:
                return DeskConstants.YUANCAI;
            case DeskConstants.DESK_XIANCHANG:
                return DeskConstants.XIANCHANG;
            default:
                return null;
        }


    }

    @Override
    public CellData convertToExcelData(Integer s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        switch (s) {
            case 1:
                return new CellData(DeskConstants.Desk_YUANCAI);
            case 2:
                return new CellData(DeskConstants.DESK_XIANCHANG);
            default:
                return null;
        }


    }

    //将java的数据类型转为excel数据类型
}
