package com.qk.plugin.dataservice.apisix.route.constant;

public class ApiSixConstant {

  // ROUTE
  public static final String API_SIX_ADMIN_ROUTE_URL_KEY = "API_SIX_ADMIN_ROUTE_URL_KEY";
  public static final String API_SIX_HEAD_KEY = "X-API-KEY";
  public static final String API_SIX_API_VERSION_KEY = "API_VERSION";
  public static final String API_SIX_ROUTE_ID = "routeId";

  // CONSUMER
  public static final String API_SIX_ADMIN_CONSUMER_URL_KEY = "API_SIX_ADMIN_CONSUMER_URL_KEY";
  public static final String API_SIX_CONSUMER_PLUGINS_KEY_AUTH = "key-auth";
  public static final String API_SIX_KEY_AUTH_POSITION_HEADER_KEY = "header";
  public static final String API_SIX_KEY_AUTH_POSITION_HEADER_VAL = "Authorization";
  public static final String API_SIX_KEY_AUTH_POSITION_QUERY_KEY = "query";

  // UPSTREAM
  public static final String API_SIX_ADMIN_UPSTREAM_URL_KEY = "API_SIX_ADMIN_UPSTREAM_URL_KEY";

  // UPSTREAM
  public static final String API_SIX_ADMIN_SERVER_URL_KEY = "API_SIX_ADMIN_SERVER_URL_KEY";

  // 插件信息PLUGINS_LIMIT_COUNT
  public static final String PLUGINS_LIMIT_COUNT_KEY = "limit-count";
  public static final String PLUGINS_LIMIT_COUNT_KEY_TYPE = "var";
  public static final String PLUGINS_LIMIT_COUNT_REMOTE_ADDR = "remote_addr";
  public static final String PLUGINS_LIMIT_COUNT_LOCAL = "local";
  public static final int PLUGINS_LIMIT_COUNT_REJECTED_CODE = 503;
}
