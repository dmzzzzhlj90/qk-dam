package com.qk.dm.dataservice.constant;

import java.util.LinkedList;
import java.util.List;

public class DasConstant {
    /**
     * 目录顶级层级父级id
     */
    public static final String TREE_DIR_TOP_PARENT_ID = "-1";

    /**
     * 分页默认参数设置,当前页1,每页10条,根据ID进行排序
     */
    public static final int PAGE_DEFAULT_NUM = 1;

    public static final int PAGE_DEFAULT_SIZE = 10;
    public static final String PAGE_DEFAULT_SORT = "id";

    // API路由匹配,无API_ROUTE_ID,默认值为0
    public static final String DEFAULT_API_ROUTE_ID = "0";

    // 新建API_字段操作符号
    public static List<String> getDasApiCreateParamCompareSymbol() {
        LinkedList<String> compareSymbolList = new LinkedList<>();
        compareSymbolList.add("=");
        compareSymbolList.add("<>");
        compareSymbolList.add(">");
        compareSymbolList.add(">=");
        compareSymbolList.add("<");
        compareSymbolList.add("<=");
        compareSymbolList.add("%like%");
        compareSymbolList.add("%like");
        return compareSymbolList;
    }

    // API类型-参数数据类型
    public static final String DAS_API_PARA_COL_TYPE_STRING = "STRING";
    public static final String DAS_API_PARA_COL_TYPE_INTEGER = "INTEGER";

    // 统一前端key值定义
    public static final String PARAM_KEY = "key";
    public static final String PARAM_VALUE = "value";
    public static final String PARAM_REQUIRED = "required";


}
