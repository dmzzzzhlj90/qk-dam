package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 数据质量_规则调度_基础信息
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public interface DqcSchedulerBasicInfoService {

    PageResultVO<DqcSchedulerBasicInfoVO> searchPageList(DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO);

    void insert(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

    void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

    void update(DqcSchedulerBasicInfo dqcSchedulerBasicInfo);

    void deleteOne(Long id);

    void deleteBulk(List<DqcSchedulerBasicInfo> schedulerBasicInfoList);

    DqcSchedulerBasicInfo getInfoById(Long id);

    List<DqcSchedulerBasicInfo> getInfoList(String ids);

    DqcSchedulerBasicInfo getBasicInfo(Long id);

    List<DqcSchedulerBasicInfo> getInfoByDirId(String dirId);

    Long getCount();

    List<DqcSchedulerBasicInfo> getBasicInfoList(Set<Long> codeSet);
}
