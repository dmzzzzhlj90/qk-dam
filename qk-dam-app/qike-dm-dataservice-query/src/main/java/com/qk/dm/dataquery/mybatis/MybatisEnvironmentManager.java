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
  public void registerEnvironment(String connId, DataSource dataSource) {
    JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
    Environment environment =
            new Environment(connId, jdbcTransactionFactory, dataSource);
    envIdDatasource.put(connId, environment);
    jdbcTransactionFactoryMap.put(connId, jdbcTransactionFactory);
  }

  public JdbcTransactionFactory getJdbcTransactionFactory(String connId){
    return jdbcTransactionFactoryMap.get(connId);
  }

  public Environment getEnvironment(String connId){
    return envIdDatasource.get(connId);
  }
  public void bindEnvironment(BiConsumer<String, Environment> biConsumer) {
    envIdDatasource.forEach(biConsumer);
  }
}
