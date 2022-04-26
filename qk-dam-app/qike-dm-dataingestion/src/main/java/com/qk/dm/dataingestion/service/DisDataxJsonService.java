package com.qk.dm.dataingestion.service;

public interface DisDataxJsonService {

    void insert(Long baseInfoId,String dataxJson);

    void update(Long baseInfoId,String dataxJson);

    String findDataxJson(Long baseInfoId);
}
