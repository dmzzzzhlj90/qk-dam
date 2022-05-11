package com.qk.dam.datasource.service;

import com.qk.dam.datasource.pojo.ConnectInfoVo;

import java.util.List;

public interface MetadataApiService {
  //获取库名称信息
  List<String> queryDB(ConnectInfoVo connectInfoVo);
  //获取表名称信息
  List<String> queryTable(ConnectInfoVo connectInfoVo);
}
