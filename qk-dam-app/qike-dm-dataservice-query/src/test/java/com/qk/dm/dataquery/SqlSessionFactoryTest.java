package com.qk.dm.dataquery;

import com.qk.dm.dataquery.model.Mapper;
import com.qk.dm.dataquery.model.MapperSelect;
import com.qk.dm.dataquery.model.ResultMap;
import com.qk.dm.dataquery.util.MapperUtil;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.DriverDataSource;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SpringBootTest(classes = DmDataServiceQueryApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Disabled
class SqlSessionFactoryTest {

    static final HikariDataSource hikariDataSource = new HikariDataSource();
    static final Configuration configuration = new Configuration();
    static final Mapper.MapperBuilder mapperBuilder = Mapper.builder();
    static SqlSession sqlSession;


    @BeforeAll
    static void before() {
        //fixme 此处为配置的数据源信息
        hikariDataSource.setDataSource(new DriverDataSource(
                "jdbc:mysql://172.21.33.141:3306/qkdam?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                "com.mysql.cj.jdbc.Driver",
                new Properties(),
                "root",
                "JMFIuOx2"

        ));

        //fixme 此处为配置数据查询的环境信息
        Environment development = new Environment(
                "development",
                new JdbcTransactionFactory(),
                hikariDataSource
        );
        configuration.setEnvironment(development);

        Mapper mapper1 = mapperBuilder.namespace("xx")
                .select(List.of(
                        //fixme 此处为查询语句适配
                        new MapperSelect("findAll", "mmm",null, " select * from qk_source where 1=1 \n <if test='pid==2'> and id=#{pid}</if>")
                ))
                .resultMap(List.of(
                        //fixme 配置返回类型
                        ResultMap.builder()
                                .autoMapping(true)
                                .id("mmm")
                                .type("HashMap")
                                .result(List.of(
                                        //todo 此处为页面配置持久化关系映射
                                        new ResultMap.Result("id", "cc"),
                                        new ResultMap.Result("name", "nn")
                                )).build()
                ))
                .build();

        //todo 绑定mybatis configuration 获取 SqlSessionFactory
        XMLMapperBuilder mapperParser = new XMLMapperBuilder(
                new ByteArrayInputStream(MapperUtil.getMapperXmlStr(mapper1).getBytes(StandardCharsets.UTF_8))
                , configuration
                , null
                , configuration.getSqlFragments());
        mapperParser.parse();
    }

    @Test
    void testSqlSession() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        sqlSession = sqlSessionFactory.openSession();
        List<Map> qkSources = sqlSession.selectList("xx.findAll", Map.of("pid", 2));
//        logger.info("查询测试的数据「"+qkSources+"」");
        System.out.println(qkSources);
    }

    @AfterAll
   static void after() {
        sqlSession.close();
    }
}
