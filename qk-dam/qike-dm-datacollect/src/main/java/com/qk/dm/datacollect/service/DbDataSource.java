package com.qk.dm.datacollect.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DbDataSource {
  List<String> getResultDb(String dataSourceName);
}
