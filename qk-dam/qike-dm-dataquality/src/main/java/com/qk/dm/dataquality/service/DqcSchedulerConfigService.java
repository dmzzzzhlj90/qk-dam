package com.qk.dm.dataquality.service;//package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据质量_规则调度_配置信息
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public interface DqcSchedulerConfigService {

    PageResultVO<DqcSchedulerConfigVO> searchPageList(DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO);

    void insert(DqcSchedulerConfigVO dqcSchedulerConfigVO);

    void update(DqcSchedulerConfigVO dqcSchedulerConfigVO);

    void delete(String taskId);

    void deleteBulk(List<String> taskIds);

}
