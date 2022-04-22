package com.qk.dm.dataquery.mybatis;

import com.qk.dm.dataquery.event.DatasourceEvent;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.context.event.EventListener;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * @author zhudaoming
 */
public class MybatisEnvironmentManager {
  /** 事务管理器 */
  private final Map<String, JdbcTransactionFactory> jdbcTransactionFactoryMap =
      new ConcurrentHashMap<>(256);

  private final Map<String, Environment> envIdDatasource = new ConcurrentHashMap<>(256);
  @EventListener
  public void onEnvironmentInsert(DatasourceEvent.EnvironmentInsertEvent environmentInsertEvent) {
    environmentInsertEvent.getDataSourceMap().forEach(this::registerEnvironment);
  }
  public void registerEnvironment(String connectName, DataSource dataSource) {
    JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
    Environment environment =
            new Environment(connectName, jdbcTransactionFactory, dataSource);
    envIdDatasource.put(connectName, environment);
    jdbcTransactionFactoryMap.put(connectName, jdbcTransactionFactory);
  }

  public JdbcTransactionFactory getJdbcTransactionFactory(String connectName){
    return jdbcTransactionFactoryMap.get(connectName);
  }

  public Environment getEnvironment(String connectName){
    return envIdDatasource.get(connectName);
  }
  public void bindEnvironment(BiConsumer<String, Environment> biConsumer) {
    envIdDatasource.forEach(biConsumer);
  }
}
