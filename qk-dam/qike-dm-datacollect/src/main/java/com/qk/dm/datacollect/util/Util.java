package com.qk.dm.datacollect.util;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;

import java.util.Map;

/**
 * @author zys
 * @date 2022/4/18 16:25
 * @since 1.0.0
 */
public class Util {
  public static Map<String,String> getDataSourceInfo(
      ResultDatasourceInfo dataSourceName) {
   return GsonUtil.fromJsonString(dataSourceName.getConnectBasicInfoJson(), new TypeToken<Map<String, String>>() {
    }.getType());
  }
}