package com.qk.dm.dataservice.utils;

import com.qk.dm.dataservice.constant.DataBaseDataTypeEnum;
import com.qk.dm.dataservice.constant.OperationSymbolEnum;
import com.qk.dm.dataservice.vo.DasApiCreateRequestParasVO;
import com.qk.dm.dataservice.vo.DasApiCreateResponseParasVO;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 执行SQL工具类
 *
 * @author wjq
 * @date 2022/03/04
 * @since 1.0.0
 */
public class SqlExecuteUtils {

    public static final String TAB_KEY = "${TAB}";
    public static final String COL_LIST_KEY = "${COL_LIST}";


    public static final String COL_LIST_DEFAULT = "*";
    public static final String SINGLE_TABLE_SELECT_SQL_TEMPLATE = "SELECT ${COL_LIST} FROM ${TAB} WHERE 1=1 ";
    public static final String AND = " AND ";
    public static final String APOSTROPHE_STRING = "'";
    public static final String PERCENT_SIGN = "%";
    public static final String LIKE = " like ";
    public static final String SPACE = " ";

    /**
     * 分页
     */
    public static final String PAGE_NUM = "page_num";
    public static final String PAGE_SIZE = "page_size";
    public static final String LIMIT = " LIMIT ";

    public static final String ORDER_BY = " ORDER BY ";

    public static final String DOLLAR_SYMBOL = "$";
    public static final String LEFT_BIG_BRACKETS = "{";
    public static final String RIGHT_BIG_BRACKETS = "}";
    public static final String AS = "as";


    /*********************************************MYSQL******************************************************/
    /**
     * MySQL生成查询sql
     *
     * @param tableName     表名称
     * @param reqParams     请求参数
     * @param responseParas 响应参数
     * @param mappingParams 参数字段映射关系
     * @param orderByStr    排序SQL
     * @return
     */
    public static String mysqlExecuteSQL(String tableName,
                                         Map<String, String> reqParams,
                                         List<DasApiCreateResponseParasVO> responseParas,
                                         Map<String, List<DasApiCreateRequestParasVO>> mappingParams,
                                         String orderByStr) {
        String sql = SINGLE_TABLE_SELECT_SQL_TEMPLATE;
        // 表名称
        sql = sql.replace(TAB_KEY, tableName);
        // 查询字段
        sql = sqlSelectResCol(responseParas, sql);
        // where条件
        sql = sql + sqlWherePara(reqParams, mappingParams);
        //排序
        sql = sql + orderByStr;
        //分页查询
        sql = sql + getPageSqlPart(reqParams);
        return sql;
    }

    /**
     * 查询字段
     *
     * @param responseParas
     * @param sql
     * @return
     */
    private static String sqlSelectResCol(List<DasApiCreateResponseParasVO> responseParas, String sql) {
        if (responseParas.size() > 0) {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < responseParas.size(); i++) {
                DasApiCreateResponseParasVO responsePara = responseParas.get(i);
                if (DataBaseDataTypeEnum.DATE.getCode().equalsIgnoreCase(responsePara.getParaType())) {
                    // date
                    strBuilder.append(" date_format(")
                            .append(responsePara.getMappingName())
                            .append(",'")
                            .append(DataBaseDataTypeEnum.DATE.getValue()).append("') " + AS + " ")
                            .append(responsePara.getParaName()).append(" ");
                } else if (DataBaseDataTypeEnum.DATETIME.getCode().equalsIgnoreCase(responsePara.getParaType())) {
                    // datetime
                    strBuilder.append(" date_format(")
                            .append(responsePara.getMappingName()).append(",'")
                            .append(DataBaseDataTypeEnum.DATETIME.getValue()).append("') ").append(AS).append(" ")
                            .append(responsePara.getParaName()).append(" ");
                } else {
                    strBuilder.append(" ").append(responsePara.getMappingName()).append(" ");
                }
                // 逗号
                if (i != responseParas.size() - 1) {
                    strBuilder.append(",");
                }
            }
            sql = sql.replace(COL_LIST_KEY, strBuilder.toString());
        }
        // 默认全部字段查询
        sql = sql.replace(COL_LIST_KEY, COL_LIST_DEFAULT);
        return sql;
    }

    /**
     * where条件
     *
     * @param reqParams
     * @param mappingParams
     * @return
     */
    private static StringBuilder sqlWherePara(Map<String, String> reqParams, Map<String, List<DasApiCreateRequestParasVO>> mappingParams) {
        StringBuilder whereBuffer = new StringBuilder();
        if (mappingParams.size() > 0) {
            for (String paraName : mappingParams.keySet()) {
                DasApiCreateRequestParasVO apiCreateRequestParasVO = mappingParams.get(paraName).get(0);
                // 数据库对应字段
                String column = apiCreateRequestParasVO.getMappingName();
                // 请求参数
                String value = reqParams.get(paraName);
                // 操作符
                String conditionType = apiCreateRequestParasVO.getConditionType();
                //字段映射操作比较符号
                mysqlSwitchOperationSymbol(whereBuffer, column, value, conditionType);
            }
        }
        return whereBuffer;
    }

    /**
     * 字段映射操作比较符号
     *
     * @param whereBuffer
     * @param column
     * @param value
     * @param conditionType
     */
    private static void mysqlSwitchOperationSymbol(StringBuilder whereBuffer, String column, String value, String conditionType) {
        String condition = null;
        OperationSymbolEnum symbolEnum = OperationSymbolEnum.getVal(conditionType);
        switch (Objects.requireNonNull(symbolEnum)) {
            case NO_GREATER_LESS_THAN:
                condition = OperationSymbolEnum.NO_GREATER_LESS_THAN.getValue();
                value = APOSTROPHE_STRING + value + APOSTROPHE_STRING;
                break;
            case MORE_THAN:
                condition = OperationSymbolEnum.MORE_THAN.getValue();
                value = APOSTROPHE_STRING + value + APOSTROPHE_STRING;
                break;
            case LESS_THAN:
                condition = OperationSymbolEnum.LESS_THAN.getValue();
                value = APOSTROPHE_STRING + value + APOSTROPHE_STRING;
                break;
            case LESS_THAN_EQUAL:
                condition = OperationSymbolEnum.LESS_THAN_EQUAL.getValue();
                value = APOSTROPHE_STRING + value + APOSTROPHE_STRING;
                break;
            case ALL_LIKE:
                condition = LIKE;
                value = APOSTROPHE_STRING + PERCENT_SIGN + value + PERCENT_SIGN + APOSTROPHE_STRING;
                break;
            case LEFT_LIKE:
                condition = LIKE;
                value = APOSTROPHE_STRING + value + PERCENT_SIGN + APOSTROPHE_STRING;
                break;
            default:
                condition = OperationSymbolEnum.EQUAL.getValue();
                value = APOSTROPHE_STRING + value + APOSTROPHE_STRING;
                break;
        }
        whereBuffer.append(AND).append(column).append(condition).append(SPACE).append(value);
    }

    /**
     * 执行SQL片段中的参数替换
     *
     * @param sqlPara    输入SQL片段
     * @param reqParams  真实请求参数
     * @param orderByStr 排序SQL
     * @return
     */
    public static String mysqlSqlPara(String sqlPara, Map<String, String> reqParams, String orderByStr) {
        String replaceSql = sqlPara;
        for (String para : reqParams.keySet()) {
            String value = reqParams.get(para);
            if (!ObjectUtils.isEmpty(value)) {
                replaceSql = replaceSql.replace(DOLLAR_SYMBOL + LEFT_BIG_BRACKETS + para + RIGHT_BIG_BRACKETS,
                        APOSTROPHE_STRING + value + APOSTROPHE_STRING);
            }
            //TODO sql查询条件设置对应参数,调试接口传递空值查询失败
        }

        //排序
        replaceSql = replaceSql + orderByStr;
        //分页查询
        replaceSql = replaceSql + getPageSqlPart(reqParams);
        return replaceSql;
    }

    /**
     * 分页查询
     *
     * @param reqParams
     * @return
     */
    private static String getPageSqlPart(Map<String, String> reqParams) {
        String pageSqlPart = "";
        if (!ObjectUtils.isEmpty(reqParams.get(PAGE_NUM)) && !ObjectUtils.isEmpty(reqParams.get(PAGE_SIZE))) {
            int pageNum = Integer.parseInt(Objects.requireNonNull(reqParams.get(PAGE_NUM)));
            int pageSize = Integer.parseInt(Objects.requireNonNull(reqParams.get(PAGE_SIZE)));
            int offset = (pageNum - 1) * pageSize;
            pageSqlPart += LIMIT + offset + "," + pageSize;
        }
        return pageSqlPart;
    }

    /*********************************************HIVE*****************************************************/
    /**
     * HIVE 生成查询sql
     *
     * @param tableName     表名称
     * @param reqParams     请求参数
     * @param responseParas 响应参数
     * @param mappingParams 参数字段映射关系
     * @return
     */
    public static String hiveExecuteSQL(String tableName,
                                        Map<String, String> reqParams,
                                        List<DasApiCreateResponseParasVO> responseParas,
                                        Map<String, List<DasApiCreateRequestParasVO>> mappingParams) {
        String sql = SINGLE_TABLE_SELECT_SQL_TEMPLATE;
        // 表名称
        sql = sql.replace(TAB_KEY, tableName);

        // 查询字段
        if (responseParas.size() > 0) {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < responseParas.size(); i++) {
                //是否为date
                DasApiCreateResponseParasVO responsePara = responseParas.get(i);
                if (responsePara.getParaType().equals("datetime")) {
                    strBuilder.append("date_format(" + responsePara.getMappingName() + ",'%Y-%m-%d')");
                } else {
                    strBuilder.append(" " + responsePara.getMappingName() + " ");
                }
                // 逗号
                if (i != responseParas.size() - 1) {
                    strBuilder.append(",");
                }
            }
            sql = sql.replace(COL_LIST_KEY, strBuilder.toString());
        }
        // 默认全部字段查询
        sql = sql.replace(COL_LIST_KEY, COL_LIST_DEFAULT);

        // where条件
        if (reqParams.size() > 0) {
            StringBuilder whereBuffer = new StringBuilder();
            for (String paraName : reqParams.keySet()) {
                DasApiCreateRequestParasVO apiCreateRequestParasVO = mappingParams.get(paraName).get(0);
                // 数据库对应字段
                String column = apiCreateRequestParasVO.getMappingName();
                // 请求参数
                String value = reqParams.get(paraName);
                // 操作符
                String conditionType = apiCreateRequestParasVO.getConditionType();
                //字段映射操作比较符号
                mysqlSwitchOperationSymbol(whereBuffer, column, value, conditionType);
            }
            sql = sql + whereBuffer;
        }
        return sql;
    }

    /**
     * 字段映射操作比较符号
     *
     * @param whereBuffer
     * @param column
     * @param value
     * @param conditionType
     */
    private static void hiveSwitchOperationSymbol(StringBuilder whereBuffer, String column, String value, String
            conditionType) {
        String condition = null;
        OperationSymbolEnum symbolEnum = OperationSymbolEnum.getVal(conditionType);
        switch (Objects.requireNonNull(symbolEnum)) {
            case NO_GREATER_LESS_THAN:
                condition = OperationSymbolEnum.NO_GREATER_LESS_THAN.getValue();
                break;
            case MORE_THAN:
                condition = OperationSymbolEnum.MORE_THAN.getValue();
                break;
            case LESS_THAN:
                condition = OperationSymbolEnum.LESS_THAN.getValue();
                break;
            case LESS_THAN_EQUAL:
                condition = OperationSymbolEnum.LESS_THAN_EQUAL.getValue();
                break;
            case ALL_LIKE:
                condition = LIKE;
                value = APOSTROPHE_STRING + PERCENT_SIGN + value + PERCENT_SIGN + APOSTROPHE_STRING;
                break;
            case LEFT_LIKE:
                condition = LIKE;
                value = APOSTROPHE_STRING + value + PERCENT_SIGN + APOSTROPHE_STRING;
                break;
            default:
                condition = OperationSymbolEnum.EQUAL.getValue();
                break;
        }
        whereBuffer.append(AND).append(column).append(condition).append(value);
    }

    /**
     * 执行SQL片段中的参数替换
     *
     * @param sqlPara
     * @param reqParams
     * @return
     */
    public static String hiveSqlPara(String sqlPara, Map<String, String> reqParams) {
        String replaceSql = sqlPara;
        for (String para : reqParams.keySet()) {
            String value = reqParams.get(para);
            replaceSql = replaceSql.replace(para, value);
        }
        return replaceSql;
    }
}