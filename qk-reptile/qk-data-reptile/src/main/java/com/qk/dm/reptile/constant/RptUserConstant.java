package com.qk.dm.reptile.constant;

/**
 * 获取用户信息相关常量
 */
public class RptUserConstant {

    public static final String URL = "jdbc:mysql://172.20.0.24:3306/keycloak?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "Zhudao123!";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String CLIENT = "pachong";
    public static final String SELECT = "select * from USER_ENTITY where REALM_ID=? ";
    public static final String WHERE ="and USERNAME like ?  limit ?,?";
    public static final String PAGE = " limit ?,?";
    public static final String COUNT_WHERE = "and USERNAME like ?";
    public static final String USER_NAME = "username";
    public static final String ID = "id";

    public static final String USER_SESSION_KEY = "userName";
    public static final String BUTTON_POWER = "buttonPower";
}
