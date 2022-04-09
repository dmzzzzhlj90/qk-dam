package com.qk.dm.reptile.constant;

/**
 * 获取用户信息相关常量
 */
public class RptUserConstant {

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
