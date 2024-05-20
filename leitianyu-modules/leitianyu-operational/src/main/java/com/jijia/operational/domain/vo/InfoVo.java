package com.jijia.operational.domain.vo;

import com.jijia.common.core.web.domain.BaseEntity;

public class InfoVo extends BaseEntity {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public String toString() {
        return "InfoVo{" +
                "queryString='" + queryString + '\'' +
                '}';
    }
}

