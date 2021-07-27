package com.qk.dam.rdbmsl2atlas.repo;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlColumnType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlTableType;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MysqlTypeAgg {
    private static final Db use = Db.use("qk_es_updated_1");

    public MysqlDbType searchMedataByDb(String database,
                                        String applicationName,
                                        String serverInfo,
                                        String owner,
                                        String description,
                                        String displayName) throws SQLException {
        List<Entity> entityList = use.query("select * from TABLES im where im.table_schema=?", database);
        return getMysqlDbType(database, applicationName, serverInfo, owner, description, displayName, entityList);

    }

    public MysqlDbType searchMedataByDb(String database,
                                        String patternTableStr,
                                        String applicationName,
                                        String serverInfo,
                                        String owner,
                                        String description,
                                        String displayName) throws SQLException {
        List<Entity> entityList = use.query("select * from TABLES im where im.table_schema=? and im.table_name=?", database,patternTableStr);
        return getMysqlDbType(database, applicationName, serverInfo, owner, description, displayName, entityList);

    }

    private MysqlDbType getMysqlDbType(String database, String applicationName, String serverInfo, String owner, String description, String displayName, List<Entity> entityList) {
        // TODO 数据库信息从数据应用系统名录获得

        MysqlDbType mysqlDbType = MysqlDbType.builder()
                .applicationName(applicationName)
                .serverInfo(serverInfo)
                .name(database)
                .owner(owner)
                .description(description)
                .displayName(displayName)
                .qualifiedName(database + "@" + serverInfo).build();

        List<MysqlTableType> mysqlTableTypes = entityList.stream().map(entity -> {
            MysqlTableType mysqlTableType = MysqlTableType.builder()
                    .name(entity.getStr("table_name"))
                    .type(entity.getStr("table_type"))
                    .dataLength(entity.getLong("data_length"))
                    .indexLength(entity.getLong("index_length"))
                    .createTime(entity.getLong("create_time"))
                    .updateTime(entity.getLong("update_time"))
                    .tableCollation(entity.getStr("table_collation"))
                    .tableRows(entity.getStr("table_rows"))
                    .comment(entity.getStr("table_comment"))
                    .owner(owner)
                    .description(entity.getStr("table_comment"))
                    .displayName(entity.getStr("table_comment"))
                    .qualifiedName(database + "." + entity.getStr("table_name") + "@172.31.0.16")
                    .build();
            try {
                List<Entity> column = use.query("select * from COLUMNS where table_schema=?  and table_name=?", database, mysqlTableType.getName());

                List<MysqlColumnType> columnTypeList = column.stream().map(colEntity -> MysqlColumnType.builder()
                        .name(colEntity.getStr("column_name"))
                        .position(colEntity.getInt("ordinal_position"))
                        .isNullable(colEntity.getStr("is_nullable").equals("YES"))
                        .isPrimaryKey(colEntity.getStr("column_key") != null && colEntity.getStr("column_key").equals("PRI"))
                        .data_type(colEntity.getStr("column_type"))
                        .extra(colEntity.getStr("extra"))
                        .default_value(colEntity.getStr("column_default"))
                        .displayName(colEntity.getStr("column_comment"))
                        .description(colEntity.getStr("column_comment"))
                        .owner(owner)
                        .qualifiedName(database + "." + mysqlTableType.getName() + "." + colEntity.getStr("column_name") + "@172.31.0.16")
                        .build()).collect(Collectors.toList());
                mysqlTableType.setMysqlColumnTypes(columnTypeList);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return mysqlTableType;
        }).collect(Collectors.toList());

        mysqlDbType.setMysqlTableTypes(mysqlTableTypes);
        return mysqlDbType;
    }

}
