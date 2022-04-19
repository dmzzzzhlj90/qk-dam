package com.qk.dm.dataservice.biz;

import com.qk.dm.dataservice.dto.ApiRegisterDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 批量处理数据
 *
 * @author wjq
 * @date 2022/3/2
 * @since 1.0.0
 */
@Component
public class BulkProcessApiDataService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 批量新增注册API信息
     *
     * @param saveDataList
     */
    @Transactional(rollbackFor = Exception.class)
    public void bulkSaveRegisterApi(List<ApiRegisterDTO> saveDataList) {
        for (ApiRegisterDTO apiRegisterDTO : saveDataList) {
            // API基础信息 insert操作
            entityManager.persist(apiRegisterDTO.getDasApiBasicInfo());
            // 注册API insert操作
            entityManager.persist(apiRegisterDTO.getDasApiRegister());
        }
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * 批量更新注册API信息
     *
     * @param updateDataList
     */
    @Transactional(rollbackFor = Exception.class)
    public void bulkModifySRegisterApi(List<ApiRegisterDTO> updateDataList) {
        for (ApiRegisterDTO apiRegisterDTO : updateDataList) {
            // API基础信息 update操作
            entityManager.merge(apiRegisterDTO.getDasApiBasicInfo());
            // 注册API update操作
            entityManager.merge(apiRegisterDTO.getDasApiRegister());
        }
        entityManager.flush();
        entityManager.clear();
    }

}
