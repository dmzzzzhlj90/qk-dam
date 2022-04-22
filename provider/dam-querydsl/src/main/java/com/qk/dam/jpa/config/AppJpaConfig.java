package com.qk.dam.jpa.config;

import com.qk.dam.datasource.config.DatasourceActive;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * @author daomingzhu
 * @date 2021/06/02
 * @since 1.0.0 spring data jpa 配置
 */
@Configuration
@EnableJpaRepositories("com.qk.**.repositories")
public class AppJpaConfig {
  private final Map<Database, DataSource> dataSourceMap = new ConcurrentHashMap<>(4);
  private Database database;

  public AppJpaConfig(
      DatasourceActive datasourceActive,
      DataSource mysqlDataSource,
      DataSource postgreSqlSource,
      DataSource oracleSqlSource) {
    dataSourceMap.put(Database.MYSQL, mysqlDataSource);
    dataSourceMap.put(Database.POSTGRESQL, postgreSqlSource);
    dataSourceMap.put(Database.ORACLE, oracleSqlSource);
    this.database = Database.valueOf(datasourceActive.getActive().toUpperCase());
  }

  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    var jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setDatabase(database);
    return jpaVendorAdapter;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    var lemfb = new LocalContainerEntityManagerFactoryBean();

    lemfb.setDataSource(dataSourceMap.get(database));
    lemfb.setJpaVendorAdapter(jpaVendorAdapter());
    lemfb.setPackagesToScan("com.qk");
    return lemfb;
  }

  @Bean
  public JPAQueryFactory jpaQueryFactory(final EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }
}
