package com.qk.dm.dataquery.mybatis;

import com.qk.dm.dataquery.domain.Mapper;
import com.qk.dm.dataquery.util.MapperUtil;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhudaoming
 */
public class DataServiceSqlSessionFactory {
    private final Map<String, SqlSessionFactory> sqlSessionFactoryMap = new ConcurrentHashMap<>(256);

    public void registerEnvironment(String connectName, SqlSessionFactory sqlSessionFactory){
        sqlSessionFactoryMap.put(connectName,sqlSessionFactory);
    }

    public SqlSessionFactory getSqlSessionFactory(String connectName){
       return sqlSessionFactoryMap.get(connectName);
    }
    public Configuration getConfiguration(String connectName){
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryMap.get(connectName);

        return sqlSessionFactory.getConfiguration();
    }

    public void scanMappers(MybatisMapperContainer mybatisMapperContainer) {

        sqlSessionFactoryMap.forEach((connectName,sqlSessionFactory)->{
            Mapper mapper = mybatisMapperContainer.getMapper(connectName);
            Configuration configuration = sqlSessionFactory.getConfiguration();
            bindMybatisConfiguration(configuration,mapper);
        });

    }

    private void bindMybatisConfiguration(Configuration configuration,Mapper mapper) {
        XMLMapperBuilder mapperParser = new XMLMapperBuilder(
                new ByteArrayInputStream(MapperUtil.getMapperXmlStr(mapper).getBytes(StandardCharsets.UTF_8))
                , configuration
                , null
                , configuration.getSqlFragments());
        mapperParser.parse();
    }
}
