package com.qk.dm.dataquality.vo.statistics.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceSearchDTO;
import com.qk.dm.dataquality.entity.DqcRuleDir;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.mapstruct.mapper.DqcProcessInstanceMapper;
import com.qk.dm.dataquality.service.DqcRuleDirService;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.impl.DolphinScheduler;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.statistics.InstanceVO;
import com.qk.dm.dataquality.vo.statistics.RuleDirVO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2021/12/23 8:26 下午
 * @since 1.0.0
 */
@Component
public class InstanceHandler {
    private static final int pageNo = 1;
    private static final int pageSize = 1;
    private static final int listPageSize = 10;

    private final DolphinScheduler dolphinScheduler;
    private final DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService;
    private final DqcRuleDirService dqcRuleDirService;

    public InstanceHandler(DolphinScheduler dolphinScheduler,
                           DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService,
                           DqcRuleDirService dqcRuleDirService) {
        this.dolphinScheduler = dolphinScheduler;
        this.dqcSchedulerBasicInfoService = dqcSchedulerBasicInfoService;
        this.dqcRuleDirService = dqcRuleDirService;
    }

    private ProcessInstanceSearchDTO getInstanceSearchDTO(int pageSize, String failure, Date date) {
        return ProcessInstanceSearchDTO.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .stateType(failure)
                .startDate(date != null ? DateUtil.formatDateTime(DateUtil.beginOfDay(date)) : null)
                .endDate(date != null ? DateUtil.formatDateTime(DateUtil.endOfDay(date)) : null)
                .build();
    }

    private ProcessInstanceResultDTO getProcessInstanceResult(ProcessInstanceSearchDTO processInstanceSearchDTO) {
        //实例数
        return dolphinScheduler.instanceList(processInstanceSearchDTO);
    }

    private ProcessInstanceResultDTO getProcessInstance(String failure) {
        return getProcessInstanceResult(getInstanceSearchDTO(pageSize, failure, null));
    }

    public InstanceVO instanceStatistics() {
        return InstanceVO.builder()
                .count(getProcessInstance(null).getTotal())
                .successCount(getProcessInstance(InstanceStateTypeEnum.SUCCESS.getCode()).getTotal())
                .failureCount(getProcessInstance(InstanceStateTypeEnum.FAILURE.getCode()).getTotal())
                .build();
    }

    /**************************流程实例列表********************************************************/

    private void getInstanceList(ProcessInstanceSearchDTO instanceSearchDTO, List<DqcProcessInstanceVO> totalList) {
        ProcessInstanceResultDTO instanceResultDTO = dolphinScheduler.instanceList(instanceSearchDTO);
        totalList.addAll(DqcProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(instanceResultDTO.getTotalList()));
        if (instanceResultDTO.getTotalPage() > instanceResultDTO.getCurrentPage()) {
            instanceSearchDTO.setPageNo(instanceSearchDTO.getPageNo() + 1);
            getInstanceList(instanceSearchDTO, totalList);
        }
    }

    public List<DqcProcessInstanceVO> getInstanceList(Date date) {
        ProcessInstanceSearchDTO instanceSearchDTO = getInstanceSearchDTO(listPageSize, null, date);
        //查询出今天所有实例
        List<DqcProcessInstanceVO> totalList = new ArrayList<>();
        getInstanceList(instanceSearchDTO, totalList);
        return totalList;
    }

    /****************************************************************************************************/

    private Map<Long, String> getCodeDirMap(List<DqcProcessInstanceVO> totalList) {
        Set<Long> codeSet = totalList.stream().map(DqcProcessInstanceVO::getProcessDefinitionCode).collect(Collectors.toSet());
        //获取code->dir
        return dqcSchedulerBasicInfoService.getBasicInfoList(codeSet)
                .stream()
                .collect(Collectors.toMap(DqcSchedulerBasicInfo::getProcessDefinitionCode, DqcSchedulerBasicInfo::getDirId));
    }

    private Map<Long, Long> getCodeCount(List<DqcProcessInstanceVO> totalList) {
        return totalList.stream().collect(Collectors.groupingBy(DqcProcessInstanceVO::getProcessDefinitionCode, Collectors.counting()));
    }

    private Map<String, Long> getDirCount(Map<Long, String> codeDirMap, Map<Long, Long> codeCount) {
        //各dir运行次数 dir->count
        Map<String, Long> dirCount = new HashMap<>(32);
        for (Map.Entry<Long, String> map : codeDirMap.entrySet()) {
            dirCount.put(map.getValue(), dirCount.get(map.getValue()) != null ? dirCount.get(map.getValue()) + codeCount.get(map.getKey()) : codeCount.get(map.getKey()));
        }
        return dirCount;
    }

    private Map<String, String> getDqcRuleDirMap(Set<String> dirIds) {
        //根据dirid 查询所有分类
        return dqcRuleDirService.getListByDirIds(dirIds)
                .stream()
                .collect(Collectors.toMap(DqcRuleDir::getRuleDirId, DqcRuleDir::getRuleDirName));
    }

    private List<RuleDirVO> getRuleDirList(List<DqcProcessInstanceVO> totalList, Map<String, Long> dirCount, Map<String, String> dqcRuleDirMap) {
        List<RuleDirVO> ruleDirList = new ArrayList<>();
        //todo 这里是以数据库存在数据为准还是调度平台查询出的数据为准
        long sum = dirCount.values().stream().mapToLong(l -> l).sum();
        if (sum > 0) {
            //当天执行总数量,直接缩小100倍算出百分比
            double count = NumberUtil.div(sum, 100, 2);
            //计算百分比
            for (Map.Entry<String, Long> item : dirCount.entrySet()) {
                ruleDirList.add(
                        RuleDirVO.builder()
                                .type(dqcRuleDirMap.get(item.getKey()))
                                .value(NumberUtil.div(item.getValue().doubleValue(), count, 2))
                                .build()
                );
            }
        }
        return ruleDirList;
    }

    public List<RuleDirVO> dirStatistics(Date date) {
        //查询当日所有实例
        List<DqcProcessInstanceVO> totalList = getInstanceList(date);
        //获取code->dir
        Map<Long, String> codeDirMap = getCodeDirMap(totalList);
        //各code运行次数 code->count
        Map<Long, Long> codeCount = getCodeCount(totalList);
        //从所有实例中结合本地数据查询各dir运行次数 dir->count
        Map<String, Long> dirCount = getDirCount(codeDirMap, codeCount);
        //获取所有分类目录 dirId->dirName
        Map<String, String> dqcRuleDirMap = getDqcRuleDirMap(new HashSet<>(dirCount.keySet()));
        //封装类
        return getRuleDirList(totalList, dirCount, dqcRuleDirMap);
    }
}
