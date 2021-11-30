package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerBasicInfoReleaseDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerBasicInfoRuningDTO;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
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

  PageResultVO<DqcSchedulerBasicInfoVO> searchPageList(DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO);

  void insert(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

  void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

  void deleteOne(Long id);

  void deleteBulk(String ids);

  void release(DqcSchedulerBasicInfoReleaseDTO dqcSchedulerBasicInfoReleaseDto);

  DqcProcessInstanceVO instanceDetailByList(Long id);

  void runing(DqcSchedulerBasicInfoRuningDTO dqcSchedulerBasicInfoRuningDTO);
}
