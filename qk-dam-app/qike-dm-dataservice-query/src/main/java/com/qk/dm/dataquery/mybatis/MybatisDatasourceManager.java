package com.qk.dm.dataquery.mybatis;

import com.google.common.collect.Lists;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.dataquery.datasouce.HikariDataSourceFactory;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import com.zaxxer.hikari.HikariConfig;
import net.sf.cglib.beans.BeanMap;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * @author zhudaoming
 */
public class MybatisDatasourceManager {

    private final List<DataQueryInfoVO> dataQueryInfo = Lists.newArrayList();
    private final Map<String, DataSource> datasource = new ConcurrentHashMap<>(256);
    private final Map<String, HikariDataSourceFactory> hikariDataSourceFactoryMap = new ConcurrentHashMap<>(256);

    public void regDataQueryInfo(List<DataQueryInfoVO> dataQueryInfoVOList) {
        dataQueryInfo.addAll(dataQueryInfoVOList);
    }

    public void regDatasource(ConnTypeEnum connType, HikariConfig hikariConfigDefault, String dataSourceName, ConnectBasicInfo connectBasicInfo) {
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl("jdbc:" + connType.getName() + "://"
                + connectBasicInfo.getServer() + ":"
                + connectBasicInfo.getPort()
        );
        hc.setDriverClassName(connectBasicInfo.getDriverInfo());
        hc.setUsername(connectBasicInfo.getUserName());
        hc.setPassword(connectBasicInfo.getPassword());
        BeanMap hcBeanMap = BeanMap.create(hc);
        BeanMap.create(hikariConfigDefault).forEach((k,v)->{
            if (Objects.nonNull(v)){
                hcBeanMap.put(k,v);
            }
        });

        HikariDataSourceFactory hikariDataSourceFactory = HikariDataSourceFactory.builder().config(hc)
                // hikariConfig 默认参数配置
                .dataSourceProperties(new Properties()).build();

        hikariDataSourceFactoryMap.put(dataSourceName, hikariDataSourceFactory);
        datasource.put(dataSourceName, hikariDataSourceFactory.dataSourceInstance());
    }

    public DataSource dataSource(String connectName) {
        return datasource.get(connectName);

    }
    public List<DataQueryInfoVO> getDataQueryInfo() {
        return dataQueryInfo;
    }

    public String getDsName(String apiId){
       return dataQueryInfo.stream().filter(dataQueryInfoVO ->
                apiId.equals(dataQueryInfoVO.getDasApiCreateSqlScript().getApiId()))
               .map(dataQueryInfoVO -> dataQueryInfoVO.getDasApiCreateSqlScript().getDataSourceName())
               .findFirst()
               .orElse("");
    }

    public String getDbName(String apiId){
        return dataQueryInfo.stream().filter(dataQueryInfoVO ->
                        apiId.equals(dataQueryInfoVO.getDasApiCreateSqlScript().getApiId()))
                .map(dataQueryInfoVO -> dataQueryInfoVO.getDasApiCreateSqlScript().getDataBaseName())
                .findFirst()
                .orElse("");
    }


    public void bindDatasource(BiConsumer<String, DataSource> biConsumer) {
        datasource.forEach(biConsumer);
    }

}
