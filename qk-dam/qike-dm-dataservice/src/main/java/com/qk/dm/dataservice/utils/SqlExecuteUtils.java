package com.qk.dm.dataservice.utils;

import com.qk.dam.dataservice.spi.pojo.RouteData;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 执行SQL工具类
 *
 * @author wjq
 * @date 2022/03/04
 * @since 1.0.0
 */
public class SqlExecuteUtils {
    public static final String SINGLE_TABLE_SELECT_SQL_TEMPLATE = "SELECT ${RESULT} FROM ${TAB} WHERE 1=1 ";



}