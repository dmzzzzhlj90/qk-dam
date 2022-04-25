package com.qk.dm.dataingestion.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.datacenter.client.ApiException;
import com.qk.dm.dataingestion.vo.*;

import java.util.Map;


public interface DataMigrationService {
    /**
     * 添加作业
     * @param dataMigrationVO
     */
    void insert(DataMigrationVO dataMigrationVO) throws ApiException;

    /**
     * 删除作业
     * @param ids
     */
    void delete(String ids);

    /**
     * 修改作业信息
     * @param dataMigrationVO
     */
    void update(DataMigrationVO dataMigrationVO) throws ApiException;

    /**
     * 查看作业详情
     * @param id
     * @return
     */
    DataMigrationVO detail(Long id);

    /**
     * 查看datax json详情
     * @param id
     * @return
     */
    Map<String,Object> jsonDetail(Long id);

    /**
     * 查询作业列表
     * @param paramsVO
     * @return
     */
    PageResultVO<DisMigrationBaseInfoVO> pageList(DisParamsVO paramsVO);

    /**
     * 获取字段列表
     * @param vo
     * @return
     */
    ColumnVO getColumnList(DisMigrationBaseInfoVO vo);

    /**
     * 运行
     * @param ids
     */
    void processRunning(String ids);

    void updateDataxJson(DisJsonParamsVO disJsonParamsVO);
}
