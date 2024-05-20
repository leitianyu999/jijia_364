package com.jijia.common.core.utils;

import com.github.pagehelper.PageHelper;
import com.jijia.common.core.utils.sql.SqlUtil;
import com.jijia.common.core.web.page.PageDomain;
import com.jijia.common.core.web.page.TableSupport;

/**
 * 分页工具类
 * 
 * @author leitianyu
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
