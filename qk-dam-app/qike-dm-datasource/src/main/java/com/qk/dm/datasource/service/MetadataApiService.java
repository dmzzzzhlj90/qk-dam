package com.qk.dm.datasource.service;

import com.qk.dam.catacollect.vo.ConnectInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MetadataApiService {
  //获取库名称信息
  List<String> queryDB(ConnectInfoVo connectInfoVo);
  //获取表名称信息
  List<String> queryTable(ConnectInfoVo connectInfoVo);
}
