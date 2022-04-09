package com.qk.dm.dataingestion.strategy;

import com.google.common.collect.Lists;
import com.qk.dm.dataingestion.model.DataxChannel;
import com.qk.dm.dataingestion.model.IngestionType;
import com.qk.dm.dataingestion.model.mysql.BasePara;
import com.qk.dm.dataingestion.model.mysql.ReaderPara;
import com.qk.dm.dataingestion.model.mysql.WriterPara;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import com.qk.dm.dataingestion.vo.DisColumnInfoVO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MysqlDataxJson implements DataxJson{
    public static String MYSQL_READER = "mysqlreader";
    public static String MYSQL_WRITER = "mysqlwriter";

    @Override
    public DataxChannel getReader(DataMigrationVO dataMigrationVO) {
        ArrayList<String> jdbcurl = Lists.newArrayList("jdbc:mysql://172.21.33.141:3306/qkdam?useUnicode=true&characterEncoding=utf-8&useSSL=fals");
        ArrayList<String> tables = Lists.newArrayList("qk_mtd_classify");
        ArrayList<BasePara.Connection> conn = Lists.newArrayList(BasePara.Connection.builder().jdbcUrl(jdbcurl)
                .table(tables).build());
        ArrayList<String> preSql = Lists.newArrayList("delete from qk_mtd_classify");

        ReaderPara reader = new ReaderPara("root", "123456", getColumnList(dataMigrationVO.getColumnList()),
                conn, "id");

        return DataxChannel.builder().name(MYSQL_READER).parameter(reader).build();

    }

    @Override
    public DataxChannel getWriter(DataMigrationVO dataMigrationVO) {
        ArrayList<String> jdbcurl = Lists.newArrayList("jdbc:mysql://172.21.33.141:3306/qkdam?useUnicode=true&characterEncoding=utf-8&useSSL=false");

        ArrayList<String> tables = Lists.newArrayList("qk_mtd_classify");
        ArrayList<BasePara.Connection> conn = Lists.newArrayList(BasePara.Connection.builder().jdbcUrl(jdbcurl)
                .table(tables).build());
        WriterPara writer = new WriterPara("root", "123456", getColumnList(dataMigrationVO.getColumnList()),
                conn, "insert");
        return DataxChannel.builder().name(MYSQL_WRITER).parameter(writer).build();
    }

    @Override
    public IngestionType ingestionType() {
        return IngestionType.MYSQL;
    }


    private List<String> getColumnList(List<DisColumnInfoVO> columnList){
        if(!CollectionUtils.isEmpty(columnList)){
            return columnList.stream().map(DisColumnInfoVO::getSourceName).collect(Collectors.toList());
        }

        return List.of();
    }
}
