package com.qk.dm.datastandards.datasource_connect.repo;

import cn.hutool.core.lang.Assert;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.datasource_connect.pojo.MysqlColumn;
import com.qk.dm.datastandards.datasource_connect.pojo.MysqlDb;
import com.qk.dm.datastandards.datasource_connect.pojo.MysqlTable;
import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import com.qk.dm.datastandards.vo.MysqlDataConnectVO;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 数据逆向功能,执行SQL聚合类
 *
 * @author wjq
 * @date 20210806
 * @since 1.0.0
 */
public class MysqlSqlAgg {
    private final Db use;
    private final String host;
    private final String db;
    private final String table;

    public MysqlSqlAgg(MysqlDataConnectVO mysqlDataConnectYamlVO, String schema) {
        this.use =
                Db.use(
                        new SimpleDataSource(
                                "jdbc:mysql://"
                                        + mysqlDataConnectYamlVO.getHost()
                                        + ":"
                                        + mysqlDataConnectYamlVO.getPort()
                                        + "/"
                                        + schema,
                                mysqlDataConnectYamlVO.getUsername(),
                                mysqlDataConnectYamlVO.getPassword()));
        host = mysqlDataConnectYamlVO.getHost();
        db = mysqlDataConnectYamlVO.getDb();
        table = mysqlDataConnectYamlVO.getTable();
    }

    public MysqlDb searchMedataByDb() {
        List<Entity> entityTableList = null;
        List<Entity> entityColumnList = null;
        String inTbs = Stream.of(table.split(",")).collect(Collectors.joining(","));
        try {
            entityTableList =
                    use.query(
                            "SELECT * FROM TABLES " + "where table_schema = ? and table_name in ( ? )",
                            db,
                            inTbs);
            Assert.notEmpty(
                    entityTableList, "未查询到符合条件的db_table元数据信息，请检查参数，错误信息db【{}】table【{}】", db, table);

            entityColumnList =
                    use.query(
                            "SELECT * FROM COLUMNS " + "where table_schema = ? and table_name in ( ? )",
                            db,
                            inTbs);
            Assert.notEmpty(
                    entityColumnList, "未查询到符合条件的db_table_column实体，请检查参数，错误信息db【{}】table【{}】", db, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getMysqlDbType(db, entityTableList, entityColumnList);
    }

    public MysqlDb searchPatternMedataByDb() {
        List<Entity> entityTableList = null;
        List<Entity> entityColumnList = null;
        if (table.equals("all")) {
            try {
                entityTableList = use.query("select * from TABLES im where im.table_schema=?", db);
                entityColumnList = use.query("select * from COLUMNS im where im.table_schema=?", db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (table.startsWith("%") || table.endsWith("%")) {
            try {
                Entity tables =
                        Entity.create("TABLES").set("table_schema", db).set("table_name", "like " + table);
                entityTableList = use.find(tables);

                Entity columns =
                        Entity.create("COLUMNS").set("table_schema", db).set("table_name", "like " + table);
                entityColumnList = use.find(columns);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Assert.notEmpty(
                entityTableList, "未查询到符合条件的db_table的元数据信息，请检查参数，错误信息db【{}】table【{}】", db, table);
        Assert.notEmpty(
                entityColumnList, "未查询到符合条件的db_table_column元数据信息，请检查参数，错误信息db【{}】table【{}】", db, table);
        return getMysqlDbType(db, entityTableList, entityColumnList);
    }

    private MysqlDb getMysqlDbType(String database, List<Entity> entityTableList, List<Entity> entityColumnList) {
        // DB
        MysqlDb mysqlDb = MysqlDb.builder().name(database).build();
        // 获取Column全部数据
        List<MysqlColumn> mysqlColumnList =
                entityColumnList.stream()
                        .map(
                                entity ->
                                        MysqlColumn.builder()
                                                .tableName(entity.getStr("table_name"))
                                                .colName(entity.getStr("column_name"))
                                                .displayName(entity.getStr("column_comment"))
                                                .data_type(entity.getStr("column_type"))
                                                .build())
                        .collect(Collectors.toList());
        Map<String, List<MysqlColumn>> columnMap =
                mysqlColumnList.stream().collect(Collectors.groupingBy(MysqlColumn::getTableName));
        // TABLE
        List<MysqlTable> mysqlTableList =
                entityTableList.stream()
                        .map(
                                entity -> {
                                    MysqlTable mysqlTable =
                                            MysqlTable.builder()
                                                    .name(entity.getStr("table_name"))
                                                    .comment(entity.getStr("table_comment"))
                                                    .build();
                                    mysqlTable.setMysqlColumns(columnMap.get(mysqlTable.getName()));
                                    return mysqlTable;
                                })
                        .collect(Collectors.toList());
        mysqlDb.setMysqlTables(mysqlTableList);

        return mysqlDb;
    }

    public List<DsdCodeInfoExt> searchCodeInfoExtValues(MysqlTable mysqlTable, Long codeInfoId) {
        List<Entity> entityList = null;
        try {
            entityList = use.query(" select * from  " + mysqlTable.getName() + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getCodeValues(entityList, mysqlTable, codeInfoId);
    }

    private List<DsdCodeInfoExt> getCodeValues(List<Entity> entityList, MysqlTable mysqlTable, Long codeInfoId) {
        if (entityList != null && entityList.size() > 0) {
            //字段信息
            List<MysqlColumn> mysqlColumnList = mysqlTable.getMysqlColumns();
            List<String> colKeyList = mysqlColumnList.stream().map(MysqlColumn::getColName).collect(Collectors.toList());
            return entityList.stream()
                    .map(
                            entity -> {
                                DsdCodeInfoExt dsdCodeInfoExt = new DsdCodeInfoExt();
                                dsdCodeInfoExt.setDsdCodeInfoId(codeInfoId);
                                // TODO 为了作为检索的参数目前支持code||name, 20210910
                                dsdCodeInfoExt.setSearchCode(entity.getStr(DsdConstant.CODE_INFO_CODE_EN_NAME));
                                dsdCodeInfoExt.setSearchValue(entity.getStr(DsdConstant.CODE_INFO_NAME_EN_NAME));
                                //扩展属性值
                                dsdCodeInfoExt.setTableConfExtValues(getTableConfExtValuesByMetaData(entity, colKeyList));
                                dsdCodeInfoExt.setGmtCreate(new Date());
                                dsdCodeInfoExt.setGmtCreate(new Date());
                                dsdCodeInfoExt.setDelFlag(0);
                                return dsdCodeInfoExt;
                            })
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private String getTableConfExtValuesByMetaData(Entity entity, List<String> colKeyList) {
        Map<String, Object> confExtValues = new HashMap<>();
        for (String colKey : colKeyList) {
            //过滤掉不展示字段
            if (filterFields(colKey)) {
                confExtValues.put(colKey, entity.getStr(colKey));
            }
        }
        return GsonUtil.toJsonString(confExtValues);
    }

    private boolean filterFields(String colKey) {
        boolean flag = true;
        if (DsdConstant.CODE_INFO_FILTER_ID.equalsIgnoreCase(colKey)) flag = false;
        //    if (DsdConstant.CODE_INFO_CODE_EN_NAME.equalsIgnoreCase(colKey)) flag = false;
        //    if (DsdConstant.CODE_INFO_VALUE_EN_NAME.equalsIgnoreCase(colKey)) flag = false;
        return flag;
    }
}
