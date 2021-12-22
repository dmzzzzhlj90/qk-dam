package com.qk.dm.dataquality.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.ruletemplate.TempTypeEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceSearchDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceSearchDTO;
import com.qk.dm.dataquality.entity.*;
import com.qk.dm.dataquality.mapstruct.mapper.DqcProcessInstanceMapper;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerBasicInfoRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.TaskInstanceVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2021/12/21 5:31 下午
 * @since 1.0.0
 */
@Service
public class DqcStatisticsServiceImpl implements DqcStatisticsService {
    private final DqcRuleTemplateRepository dqcRuleTemplateRepository;
    private final DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository;
    private final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    private final DolphinScheduler dolphinScheduler;
    private final QDqcRuleTemplate qDqcRuleTemplate = QDqcRuleTemplate.dqcRuleTemplate;
    private final QDqcSchedulerBasicInfo qDqcSchedulerBasicInfo = QDqcSchedulerBasicInfo.dqcSchedulerBasicInfo;
    private final QDqcSchedulerRules qDqcSchedulerRules = QDqcSchedulerRules.dqcSchedulerRules;


    public DqcStatisticsServiceImpl(DqcRuleTemplateRepository dqcRuleTemplateRepository,
                                    DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository,
                                    DqcSchedulerRulesRepository dqcSchedulerRulesRepository, DolphinScheduler dolphinScheduler) {
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
        this.dqcSchedulerBasicInfoRepository = dqcSchedulerBasicInfoRepository;
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dolphinScheduler = dolphinScheduler;
    }

    public void statistics() {


    }

    /**
     * 规则模版统计数据
     *
     * @return
     */
    @Override
    public Map<String, Long> ruleTemplateStatistics() {
        //规则模版总数
        long rule_temple_count = dqcRuleTemplateRepository.count();
        //规则模版内置模版数
        long rule_temple_system_count = dqcRuleTemplateRepository.count(qDqcRuleTemplate.tempType.eq(TempTypeEnum.BUILT_IN_SYSTEM.getCode()));
        //规则模版自定义模版数
        long rule_temple_custom_count = dqcRuleTemplateRepository.count(qDqcRuleTemplate.tempType.eq(TempTypeEnum.CUSTOM.getCode()));

        Map<String, Long> map = new HashMap<>(4);
        map.put("rule_temple_count", rule_temple_count);
        map.put("rule_temple_system_count", rule_temple_system_count);
        map.put("rule_temple_custom_count", rule_temple_custom_count);
        return map;
    }

    /**
     * 作业统计数据
     *
     * @return
     */
    @Override
    public Map<String, Long> basicInfoStatistics() {
        //作业总数
        long count = dqcSchedulerBasicInfoRepository.count();
        //所有表信息
        List<String> allByTables = dqcSchedulerRulesRepository.findAllByTables();
        Set<String> tables = allByTables.stream().flatMap(item -> Objects.requireNonNull(DqcConstant.jsonStrToList(item)).stream()).collect(Collectors.toSet());
        //所有字段信息
        List<String> allByFields = dqcSchedulerRulesRepository.findAllByFields();
        Set<String> fields = allByFields.stream().flatMap(item -> Objects.requireNonNull(DqcConstant.jsonStrToList(item)).stream()).collect(Collectors.toSet());
        Map<String, Long> map = new HashMap<>(4);
        map.put("basic_info_count", count);
        map.put("basic_info_tables_count", (long) tables.size());
        map.put("basic_info_fields_count", (long) fields.size());
        return map;
    }

    @Override
    public Map<String, Integer> instanceStatistics() {
        String failure = null;
        //总数
        ProcessInstanceResultDTO processInstance = dolphinScheduler.instanceList(getInstanceSearchDTO(1, 1, failure));
        //执行成功
        ProcessInstanceResultDTO processInstanceSuccess = dolphinScheduler.instanceList(getInstanceSearchDTO(1, 1, InstanceStateTypeEnum.SUCCESS.getCode()));
        //执行失败
        ProcessInstanceResultDTO processInstanceFailure = dolphinScheduler.instanceList(getInstanceSearchDTO(1, 1, InstanceStateTypeEnum.FAILURE.getCode()));
        Map<String, Integer> map = new HashMap<>(4);
        map.put("instance_count", processInstance.getTotal());
        map.put("instance_success_count", processInstanceSuccess.getTotal());
        map.put("instance_failure_count", processInstanceFailure.getTotal());
        return map;
    }

    private ProcessInstanceSearchDTO getInstanceSearchDTO(int pageNo, int pageSize, String failure) {
        return ProcessInstanceSearchDTO.builder().pageNo(pageNo).pageSize(pageSize).stateType(failure).build();
    }


    @Override
    public Map<String,Map<String,Integer>> dimensionStatistics() {
        ProcessTaskInstanceSearchDTO taskInstanceSearchDTO = getProcessTaskInstanceSearchDTO(1, 10, new Date());
        List<TaskInstanceVO> totalList = new ArrayList<>();
        //查询所有的任务实例
        getTaskInstanceList(taskInstanceSearchDTO,totalList);

        //获取所有taskCode->模版id
        Set<Long> taskCodeSet = totalList.stream().map(TaskInstanceVO::getTaskCode).collect(Collectors.toSet());
        List<DqcSchedulerRules> schedulerRulesList = (List<DqcSchedulerRules>) dqcSchedulerRulesRepository.findAll(qDqcSchedulerRules.taskCode.in(taskCodeSet));
        Map<Long, Long> rulesMap = schedulerRulesList.stream().collect(Collectors.toMap(DqcSchedulerRules::getTaskCode, DqcSchedulerRules::getRuleTempId));

        //获取所有的模版id->纬度
        List<DqcRuleTemplate> templateList = dqcRuleTemplateRepository.findAll();
        Map<Long, String> dimensionTypeMap = templateList.stream().collect(Collectors.toMap(DqcRuleTemplate::getId, DqcRuleTemplate::getDimensionType));

        //更改taskCode->模版id->纬度 为 taskCode->纬度
        Map<Long,String> rulesDimensionTypeMap = new HashMap<>();
        for(Map.Entry<Long, Long> map : rulesMap.entrySet()){
            rulesDimensionTypeMap.put(map.getKey(),dimensionTypeMap.get(map.getValue()));
        }

        //区分质量纬度成功失败状态数量
        Map<String,Integer> successMap = new HashMap<>();
        Map<String,Integer> failureMap = new HashMap<>();

        totalList.forEach(item->{
            if(item.getState().equals(InstanceStateTypeEnum.SUCCESS.getCode())){
                successMap.put(item.getState(),successMap.get(item.getState()) != null ? successMap.get(item.getState()) + 1 : 1);
            }else if(item.getState().equals(InstanceStateTypeEnum.FAILURE.getCode())){
                failureMap.put(item.getState(),failureMap.get(item.getState()) != null ? failureMap.get(item.getState()) + 1 : 1);
            }
        });

        Map<String,Map<String,Integer>> resultMap = new HashMap<>();
        resultMap.put("success",successMap);
        resultMap.put("failure",failureMap);
        return resultMap;
    }

    private void getTaskInstanceList(ProcessTaskInstanceSearchDTO taskInstanceSearchDTO, List<TaskInstanceVO> totalList) {
        ProcessTaskInstanceResultDTO taskInstanceResultDTO = dolphinScheduler.taskInstanceList(taskInstanceSearchDTO);
        totalList.addAll(DqcProcessInstanceMapper.INSTANCE.taskInstanceVO(taskInstanceResultDTO.getTotalList()));
        if (taskInstanceResultDTO.getTotalPage() > taskInstanceResultDTO.getCurrentPage()) {
            taskInstanceSearchDTO.setPageNo(taskInstanceSearchDTO.getPageNo() + 1);
            getTaskInstanceList(taskInstanceSearchDTO, totalList);
        }
    }

    private ProcessTaskInstanceSearchDTO getProcessTaskInstanceSearchDTO(int pageNo, int pageSize, Date date) {
        return ProcessTaskInstanceSearchDTO.builder().
                pageNo(pageNo).
                pageSize(pageSize).
                startDate(DateUtil.formatDateTime(DateUtil.beginOfDay(date))).
                endDate(DateUtil.formatDateTime(DateUtil.endOfDay(date)))
                .build();
    }

    @Override
    public Map<String,BigDecimal> dirStatistics() {
        List<DqcProcessInstanceVO> totalList = new ArrayList<>();
        ProcessInstanceSearchDTO instanceSearchDTO = getInstanceSearchDTO(1, 10, new Date());
        //查询出今天所有实例
        getInstanceList(instanceSearchDTO, totalList);
        //各code运行次数 code->count
        Map<Long, Long> codeCount = totalList.stream().collect(Collectors.groupingBy(DqcProcessInstanceVO::getProcessDefinitionCode, Collectors.counting()));

        //当天执行总数量
        Integer count = totalList.size();

        //拿到所有的code
        Set<Long> codeSet = totalList.stream().map(DqcProcessInstanceVO::getProcessDefinitionCode).collect(Collectors.toSet());
        //查询到所有的任务定义
        List<DqcSchedulerBasicInfo> basicInfoList = (List<DqcSchedulerBasicInfo>) dqcSchedulerBasicInfoRepository.findAll(qDqcSchedulerBasicInfo.processDefinitionCode.in(codeSet));
        //获取code->dir
//        basicInfoList.stream().collect(Collectors.toMap(DqcSchedulerBasicInfo::getProcessDefinitionCode,DqcSchedulerBasicInfo::getDirId));

        Map<String, Long> dirCount = new HashMap<>();
        //直接循环basicInfoList计算保存dir->count   dir->结果+count
        for (int i = 0; i < basicInfoList.size(); i++) {
            String dirId = basicInfoList.get(i).getDirId();
            Long code = basicInfoList.get(i).getProcessDefinitionCode();
            dirCount.put(dirId,dirCount.get(dirId) != null ? dirCount.get(dirId) + codeCount.get(code) : codeCount.get(code));
        }

        Map<String, BigDecimal> map = dirCount.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, item -> NumberUtil.div(item.getValue(), count, 2)));

        return map;
    }

    private void getInstanceList(ProcessInstanceSearchDTO instanceSearchDTO, List<DqcProcessInstanceVO> totalList) {
        ProcessInstanceResultDTO instanceResultDTO = dolphinScheduler.instanceList(instanceSearchDTO);
        totalList.addAll(DqcProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(instanceResultDTO.getTotalList()));
        if (instanceResultDTO.getTotalPage() > instanceResultDTO.getCurrentPage()) {
            instanceSearchDTO.setPageNo(instanceSearchDTO.getPageNo() + 1);
            getInstanceList(instanceSearchDTO, totalList);
        }
    }

    private ProcessInstanceSearchDTO getInstanceSearchDTO(int pageNo, int pageSize, Date date) {
        return ProcessInstanceSearchDTO.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .startDate(DateUtil.formatDateTime(DateUtil.beginOfDay(date)))
                .endDate(DateUtil.formatDateTime(DateUtil.endOfDay(date)))
                .build();
    }


}
