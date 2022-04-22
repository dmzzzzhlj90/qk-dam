package com.qk.dm.dataquery.mybatis;

import com.qk.dm.dataquery.domain.Mapper;
import com.qk.dm.dataquery.event.DatasourceEvent;
import com.qk.dm.dataquery.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.event.EventListener;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhudaoming
 */
@Slf4j
public class DataServiceSqlSessionFactory {
  private final Map<String, SqlSessionFactory> sqlSessionFactoryMap = new ConcurrentHashMap<>(256);
  @EventListener
  public void onEnvironmentInsert(DatasourceEvent.SqlSessionFactoryInsertEvent sqlSessionFactoryInsertEvent) {
    sqlSessionFactoryInsertEvent.getEnvironmentMap().forEach(this::registerEnvironment);
  }
  public void registerEnvironment(String connectName, Environment environment) {
    org.apache.ibatis.session.Configuration configuration =
        new org.apache.ibatis.session.Configuration();
    configuration.setEnvironment(environment);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    sqlSessionFactoryMap.put(connectName, sqlSessionFactory);
  }

  public SqlSessionFactory getSqlSessionFactory(String connectName) {
    return sqlSessionFactoryMap.get(connectName);
  }

  public Configuration getConfiguration(String connectName) {
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryMap.get(connectName);

    return sqlSessionFactory.getConfiguration();
  }

  public void scanMappers(MybatisMapperContainer mybatisMapperContainer) {

    sqlSessionFactoryMap.forEach(
        (connectName, sqlSessionFactory) -> {
          Mapper mapper = mybatisMapperContainer.getMapper(connectName);
          if (Objects.nonNull(mapper)) {
            Configuration configuration = sqlSessionFactory.getConfiguration();
            log.info("bind mybatis mapper to =====> configuration");
            bindMybatisConfiguration(configuration, mapper);
          }
        });
  }

  private void bindMybatisConfiguration(Configuration configuration, Mapper mapper) {
    String mapperXmlStr = MapperUtil.getMapperXmlStr(mapper);
    log.info("bind mybatis mapper to =====> configuration mapper xml content:{}",mapperXmlStr);
    byte[] mapperXmlStrBytes = mapperXmlStr.getBytes(StandardCharsets.UTF_8);
    XMLMapperBuilder mapperParser =
        new XMLMapperBuilder(
            new ByteArrayInputStream(mapperXmlStrBytes),
            configuration,
            null,
            configuration.getSqlFragments());
    mapperParser.parse();
  }
}
