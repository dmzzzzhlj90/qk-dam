package com.qk.dm.datastandards.datasource_connect.extractor;


import com.qk.dm.datastandards.datasource_connect.pojo.MysqlDb;
import com.qk.dm.datastandards.datasource_connect.pojo.MysqlTable;
import com.qk.dm.datastandards.datasource_connect.repo.MysqlSqlAgg;
import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import com.qk.dm.datastandards.vo.DataSourceJobVO;
import com.qk.dm.datastandards.vo.MysqlDataConnectVO;

import java.util.List;

/**
 * 数据逆向功能,数据源抽取类
 *
 * @author wjq
 * @date 20210806
 * @since 1.0.0
 */
public class DataSourceExtractor {
    public static final String informationSchema = "information_schema";
    private DataSourceExtractor() {
        throw new IllegalStateException("Utility class");
    }


    public static MysqlDb mysqlMetaData(DataSourceJobVO dataSourceJobVO) {
        MysqlDataConnectVO dataConnect = dataSourceJobVO.getMysqlDataConnect();
        String table = dataConnect.getTable();
        if (table.equals("all") || table.startsWith("%") || table.endsWith("%")) {
            return new MysqlSqlAgg(dataConnect,informationSchema).searchPatternMedataByDb();
        }
        return new MysqlSqlAgg(dataConnect,informationSchema).searchMedataByDb();
    }

    public static List<DsdCodeInfoExt> searchCodeInfoExtValues(DataSourceJobVO dataSourceJobVO, MysqlTable mysqlTable, Long codeInfoId) {
        return new MysqlSqlAgg(dataSourceJobVO.getMysqlDataConnect(),dataSourceJobVO.getMysqlDataConnect().getDb())
                .searchCodeInfoExtValues(mysqlTable, codeInfoId);
    }
}
