package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.stereotype.Service;

/**
 * 数据质量_规则调度_基础信息
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public interface DqcSchedulerBasicInfoService {

  PageResultVO<DqcSchedulerBasicInfoVO> searchPageList(
      DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO);

  String insert(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

  void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

  void publish(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

  void delete(Long id);

  void deleteBulk(String ids);
}
