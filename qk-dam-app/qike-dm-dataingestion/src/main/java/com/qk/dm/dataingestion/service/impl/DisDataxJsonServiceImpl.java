package com.qk.dm.dataingestion.service.impl;

import com.qk.dm.dataingestion.entity.DisDataxJson;
import com.qk.dm.dataingestion.repositories.DisDataxJsonRepository;
import com.qk.dm.dataingestion.service.DisDataxJsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * datax json
 * @author wangzp
 * @date 2022/04/26 11:31
 * @since 1.0.0
 */
@Slf4j
@Service
public class DisDataxJsonServiceImpl implements DisDataxJsonService {

    private final DisDataxJsonRepository disDataxJsonRepository;

    public DisDataxJsonServiceImpl(DisDataxJsonRepository disDataxJsonRepository) {
        this.disDataxJsonRepository = disDataxJsonRepository;
    }

    @Override
    public void insert(Long baseInfoId, String dataxJson) {
        disDataxJsonRepository.save(DisDataxJson.builder().
                baseInfoId(baseInfoId).dataxJson(dataxJson).build());
    }

    @Override
    public void update(Long baseInfoId, String dataxJson) {
        log.info("修改datax json 作业id【{}】,datax json:【{}】",baseInfoId,dataxJson);
        DisDataxJson disDataxJson = getDataxJson(baseInfoId);
        if(Objects.nonNull(disDataxJson)){
            disDataxJson.setDataxJson(dataxJson);
            disDataxJsonRepository.saveAndFlush(disDataxJson);
        }else {
            insert(baseInfoId,dataxJson);
        }
    }

    @Override
    public String findDataxJson(Long baseInfoId) {
        DisDataxJson disDataxJson = getDataxJson(baseInfoId);
        if(Objects.nonNull(disDataxJson)){
            return  disDataxJson.getDataxJson();
        }
        return null;
    }

    private DisDataxJson getDataxJson(Long baseInfoId){
        return disDataxJsonRepository.findByBaseInfoId(baseInfoId);
    }
}
