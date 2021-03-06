package com.qk.dm.dataquality.biz;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.entity.DqcRuleDir;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.service.DqcRuleDirService;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.utils.DateUtil;
import com.qk.dm.dataquality.utils.XmathUtil;
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
public class InstanceBiz {
    private final DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService;
    private final DqcRuleDirService dqcRuleDirService;
    private final RedisBiz redisBiz;
    private final WarnBiz warnBiz;

    public InstanceBiz(DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService,
                       DqcRuleDirService dqcRuleDirService,
                       RedisBiz redisBiz,
                       WarnBiz warnBiz) {
        this.dqcSchedulerBasicInfoService = dqcSchedulerBasicInfoService;
        this.dqcRuleDirService = dqcRuleDirService;
        this.redisBiz = redisBiz;
        this.warnBiz = warnBiz;
    }

    /**
     * 实例列表
     *
     * @return
     */
    public List<DqcProcessInstanceVO> getDolphinInstanceList() {
        return GsonUtil.fromJsonString(redisBiz.redisInstanceList(null, null), new TypeToken<List<DqcProcessInstanceVO>>() {}.getType());
    }

    /**********************实例统计数据************************************************************/

    private List<DqcProcessInstanceVO> getInstanceGroupByStateType(List<DqcProcessInstanceVO> dolphinInstanceList, InstanceStateTypeEnum stateType) {
        //根据状态筛选实例列表
        return dolphinInstanceList.stream().filter(it -> stateType.getCode().equals(it.getState())).collect(Collectors.toList());
    }

    public InstanceVO instanceStatistics() {
        List<DqcProcessInstanceVO> dolphinInstanceList = getDolphinInstanceList();
        return InstanceVO.builder()
                .count(dolphinInstanceList.size())
                //获取成功列表
                .successCount(getInstanceGroupByStateType(dolphinInstanceList, InstanceStateTypeEnum.SUCCESS).size())
                //获取失败列表
                .failureCount(getInstanceGroupByStateType(dolphinInstanceList, InstanceStateTypeEnum.FAILURE).size())
                .warnCount(warnBiz.warnResultList().size())
                .build();
    }

    /**********************分类统计************************************************************/

    private List<DqcProcessInstanceVO> instanceListByDate(Date date) {
        //todo hutool 转 自定义工具类
        String startTime = DateUtil.toStr(DateUtil.beginOfDay(date));
        String endTime = DateUtil.toStr(DateUtil.endOfDay(date));
//        String startTime = DateUtil.formatDateTime(DateUtil.beginOfDay(date));
//        String endTime = DateUtil.formatDateTime(DateUtil.endOfDay(date));
        return getDolphinInstanceList()
                .stream()
                .filter(it -> it.getStartTime().compareTo(startTime) >= 0 && it.getEndTime().compareTo(endTime) <= 0)
                .collect(Collectors.toList());
    }

    private Set<Long> getCodeSet(List<DqcProcessInstanceVO> successList) {
        return successList.stream()
                .map(DqcProcessInstanceVO::getProcessDefinitionCode)
                .collect(Collectors.toSet());
    }

    private Map<Long, String> getCodeDirMap(List<DqcProcessInstanceVO> totalList) {
        Set<Long> codeSet = getCodeSet(totalList);
        //获取code->dir
        return dqcSchedulerBasicInfoService.getBasicInfoList(codeSet)
                .stream()
                .collect(Collectors.toMap(DqcSchedulerBasicInfo::getProcessDefinitionCode, DqcSchedulerBasicInfo::getDirId));
    }

    private Map<Long, Long> getCodeCount(List<DqcProcessInstanceVO> totalList) {
        return totalList.stream()
                .collect(Collectors.groupingBy(DqcProcessInstanceVO::getProcessDefinitionCode, Collectors.counting()));
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
        Map<String, String> map = dqcRuleDirService.getListByDirIds(dirIds)
                .stream()
                .collect(Collectors.toMap(DqcRuleDir::getRuleDirId, DqcRuleDir::getRuleDirName));
        map.put("-1","默认分类");
        return map;
    }

    private List<RuleDirVO> getRuleDirList(List<DqcProcessInstanceVO> totalList, Map<String, Long> dirCount, Map<String, String> dqcRuleDirMap) {
        List<RuleDirVO> ruleDirList = new ArrayList<>();
        //todo 这里是以数据库存在数据为准还是调度平台查询出的数据为准
        long sum = dirCount.values().stream().mapToLong(l -> l).sum();
        if (sum > 0) {
            //当天执行总数量,直接缩小100倍算出百分比
            double count = XmathUtil.divide(sum, 100);
            //计算百分比
            for (Map.Entry<String, Long> item : dirCount.entrySet()) {
                ruleDirList.add(
                        RuleDirVO.builder()
                                .type(dqcRuleDirMap.get(item.getKey()))
                                .value(XmathUtil.divide(item.getValue().doubleValue(), count))
                                .build()
                );
            }
        }
        return ruleDirList;
    }

    public List<RuleDirVO> dirStatistics(Date date) {
        //查询当日所有实例
        List<DqcProcessInstanceVO> totalList = instanceListByDate(date);
        //获取code->dir
        Map<Long, String> codeDirMap = getCodeDirMap(totalList);
        //各code运行次数 code->count
        Map<Long, Long> codeCount = getCodeCount(totalList);
        //各dir运行次数code->dir\code->count 转  dir->count
        Map<String, Long> dirCount = getDirCount(codeDirMap, codeCount);
        //根据dir获取目录 dirId->dirName
        Map<String, String> dqcRuleDirMap = getDqcRuleDirMap(new HashSet<>(dirCount.keySet()));
        //封装计算百分比
        return getRuleDirList(totalList, dirCount, dqcRuleDirMap);
    }
}
