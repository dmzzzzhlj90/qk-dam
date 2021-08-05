package com.qk.dm.datasource.util;

import com.qk.dm.datasource.vo.DsDatasourceVO;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * 数据库测试连接
 * @author zys
 * @date 2021/8/4 10:25
 * @since 1.0.0
 */
public class DsDataSouurceConnectUtil {
    private static final Logger logger = LoggerFactory.getLogger(DsDataSouurceConnectUtil.class);
    public static final String DATA_SOURCE_MYSQL = "MySQL";
    public static final String DATA_SOURCE_ORACLE = "Oracle";
    /**
     * 判定传入的数据源连接是否测试连接成功
     * @param dsDatasourceVO
     * @return
     */
    public static Boolean getDataSourceConnect(DsDatasourceVO dsDatasourceVO) {
        Boolean connect = false;
        //获取数据库连接类型
        String linkType = dsDatasourceVO.getLinkType();
        if (StringUtils.isNotBlank(linkType)){
            if(linkType.equals(DATA_SOURCE_MYSQL)){
                connect =  connectMySQL(dsDatasourceVO);
            }
        else if(linkType.equals(DATA_SOURCE_ORACLE)){

            }
        }else {
            logger.error("数据源连接类型为空");
        }
        return connect;
    }

    /**
     * mysql做数据库连接测试
     * @return
     * @param dsDatasourceVO
     */
    private static Boolean connectMySQL(DsDatasourceVO dsDatasourceVO) {
        Boolean connect = false;
        //获取数据源连接值
        LinkedHashMap<String, String> res = dsDatasourceVO.getDataSourceValuesMap();
        String driver=(String)res.get("service");  //获取mysql数据库的驱动类
        String url="jdbc:mysql://"+(String)res.get("server")+":"+(String)res.get("port")+"/qkdam"; //连接数据库（kucun是数据库名）
        String name=(String)res.get("username");//连接mysql的用户名
        String pwd=(String)res.get("password");//连接mysql的密码
        try {
            Class.forName(driver);
            Connection conn= DriverManager.getConnection(url,name,pwd);//获取连接对象
            if (conn.isValid(0)){
                connect =true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connect;
    }
}