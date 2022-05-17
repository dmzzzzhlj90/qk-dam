package com.qk.dm.datasource.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ElasticSearchVO;
import com.qk.dam.datasource.entity.HiveInfo;
import com.qk.dam.datasource.entity.MysqlInfo;
import com.qk.dam.datasource.entity.OracleInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dam.datasource.enums.DataSourceEnum;
import com.qk.dam.datasource.enums.ExceptionEnum;
import com.qk.dam.datasource.utils.DataSourcesUtil;
import com.qk.dm.datasource.vo.DsDatasourceVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 数据库测试连接
 *
 * @author zys
 * @date 2021/8/4 10:25
 * @since 1.0.0
 */
public class DsDataSouurceConnectUtil {

    /**
     * 判定传入的数据源连接是否测试连接成功
     *
     * @param dsDatasourceVO
     * @return
     */
    public static void getDataSourceConnect(DsDatasourceVO dsDatasourceVO) {
        // 获取数据库连接类型
        String linkType = dsDatasourceVO.getLinkType();
        switch (linkType) {
            case DataSourcesUtil.MYSQL:
                connectMySQL(dsDatasourceVO, linkType);
                break;
            case DataSourcesUtil.HIVE:
                connectHive(dsDatasourceVO, linkType);
                break;
            case DataSourcesUtil.ELASTICSEARCH:
                connectEs(dsDatasourceVO, linkType);
                break;
            default:
                throw new BizException("没有匹配的数据源参数类型");
        }
    }

    /**
     * 连接es
     *
     * @param dsDatasourceVO
     * @param linkType
     */
    private static void connectEs(DsDatasourceVO dsDatasourceVO, String linkType) {
        ElasticSearchVO elasticSearchVO = new ObjectMapper().convertValue(dsDatasourceVO.getConnectBasicInfo(), ElasticSearchVO.class);
        testing(linkType, elasticSearchVO.getType(), elasticSearchVO.getServer(), elasticSearchVO.getPort(), elasticSearchVO.getUserName(), elasticSearchVO.getPassword());
    }

    /**
     * hive做数据库连接测试
     *
     * @param dsDatasourceVO
     * @param linkType
     * @return
     */
    private static void connectHive(DsDatasourceVO dsDatasourceVO,
                                    String linkType) {
        HiveInfo hiveInfo = new ObjectMapper().convertValue(dsDatasourceVO.getConnectBasicInfo(), HiveInfo.class);
        testing(linkType, hiveInfo.getType(), hiveInfo.getServer(), hiveInfo.getPort(), hiveInfo.getUserName(), hiveInfo.getPassword());
    }

    /**
     * mysql做数据库连接测试
     *
     * @param dsDatasourceVO
     * @param linkType
     * @return
     */
    private static void connectMySQL(DsDatasourceVO dsDatasourceVO,
                                     String linkType) {
        // 获取数据源连接值
        MysqlInfo mysqlInfo = new ObjectMapper().convertValue(dsDatasourceVO.getConnectBasicInfo(), MysqlInfo.class);
        testing(linkType, mysqlInfo.getType(), mysqlInfo.getServer(), mysqlInfo.getPort(), mysqlInfo.getUserName(), mysqlInfo.getPassword());
    }

    /**
     * 测试链接
     *
     * @param linkType
     * @param type
     * @param server
     * @param port
     * @param userName
     * @param password
     */
    private static void testing(String linkType, String type, String server, String port, String userName, String password) {
        String driver = DataSourceEnum.fromValue(type).getDriver(); // 获取hive数据驱动类
        String url =
                DataSourceEnum.fromValue(type).getCold()
                        + server
                        + DataSourcesUtil.DOUHAO
                        + port; // 127.0.0.1是本机地址，
        connection(url, userName, password, driver, linkType);
    }

    /**
     * 连接数据库
     *
     * @param linkType
     * @param url
     * @param user
     * @param password
     * @param driver
     * @return
     */
    private static void connection(String url, String user, String password, String driver, String linkType) {
        Connection connection = null;
        try {
            Class.forName(driver);
            // 获取连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception exception) {
            dealException(exception, linkType);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * 封装报错信息
     * @param exception
     * @param linkType
     */
    private static void dealException(Exception exception, String linkType) {
        String name = getExceptionName(exception);
        String exceptionMessage = ExceptionEnum.fromValue(linkType, name);
        throw new BizException(exceptionMessage);
    }

    private static String getExceptionName(Exception exception) {
        String exceptionName = null;
        String name = exception.getClass().getName();
        String[] split = name.split(Pattern.quote("."));
        Optional<String> exception1 = Arrays.asList(split).stream()
                .filter(s -> s.contains(DataSourcesUtil.EXCEPTION)).findFirst();
        if (exception1.isPresent()) {
            exceptionName = exception1.get();
        }
        return exceptionName;
    }

    public static Map<String, String> getDataSourceType() {
        Map<String, String> map = new HashMap();
        map.put("MYSQL", ConnTypeEnum.MYSQL.getName());
        map.put("HIVE", ConnTypeEnum.HIVE.getName());
        map.put("ELASTICSEARCH", ConnTypeEnum.ELASTICSEARCH.getName());
        return map;
    }

    public static String addDriverinfo(Object baseDataSourceTypeInfo, String type) {
        ObjectMapper objectMapper = new ObjectMapper();
        switch (type) {
            case DataSourcesUtil.MYSQL:
                MysqlInfo mysqlInfo = objectMapper.convertValue(baseDataSourceTypeInfo, MysqlInfo.class);
                mysqlInfo.setDriverInfo(DataSourceEnum.fromValue(type).getDriver());
                type = GsonUtil.toJsonString(mysqlInfo);
                break;
            case DataSourcesUtil.ORACLE:
                OracleInfo oracleInfo = objectMapper.convertValue(baseDataSourceTypeInfo, OracleInfo.class);
                oracleInfo.setDriverInfo(DataSourceEnum.fromValue(type).getDriver());
                type = GsonUtil.toJsonString(oracleInfo);
                break;
            case DataSourcesUtil.HIVE:
                HiveInfo hiveInfo = objectMapper.convertValue(baseDataSourceTypeInfo, HiveInfo.class);
                hiveInfo.setDriverInfo(DataSourceEnum.fromValue(type).getDriver());
                type = GsonUtil.toJsonString(hiveInfo);
                break;
            case DataSourcesUtil.ELASTICSEARCH:
                ElasticSearchVO elasticSearchVO = objectMapper.convertValue(baseDataSourceTypeInfo, ElasticSearchVO.class);
                elasticSearchVO.setDriverInfo(DataSourceEnum.fromValue(type).getDriver());
                type = GsonUtil.toJsonString(elasticSearchVO);
                break;
            default:
                throw new BizException("没有匹配的数据源参数类型");
        }
        return type;
    }
}
