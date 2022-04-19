package com.qk.dam.metedata.type;

/**
 * @author zhudaoming
 */
public interface AtlasEntityConstant {
    public static final String MYSQL_PROCESS = "mysql_process";
    public static final String HIVE_PROCESS = "hive_process";
    public static final String MYSQL_COLUMN_PROCESS = "mysql_column_lineage";
    public static final String HIVE_COLUMN_LINEAGE = "hive_column_lineage";
    public static final String PROCESS_ATTRIBUTE_INPUTS = "inputs";
    public static final String PROCESS_ATTRIBUTE_OUTPUTS = "outputs";
    public static final String PROCESS_START_TIME = "startTime";
    public static final String PROCESS_END_TIME = "endTime";
    public static final String OPERATIONTYPE = "operationType";
    public static final String QUERYTEXT = "queryText";
    public static final String QUERYPLAN = "queryPlan";
    public static final String QUERYID = "queryId";
    public static final String PROCESS_USER_NAME = "userName";

    public static final String ENTITY_NAME                         = "name";
    public static final String ENTITY_DESCRIPTION                  = "description";
    public static final String ENTITY_OWNER                        = "owner";
    public static final String ENTITY_CREATE_TIME                  = "createTime";
}
