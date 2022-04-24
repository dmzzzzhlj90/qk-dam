package com.qk.dm.datacollect.vo;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.dam.commons.util.GsonUtil;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 数据质量_规则调度_基础信息VO
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class DctSchedulerBasicInfoVO {

    /**
     * 调度流程实例code,修改时必填
     */
    private Long code;

    /**
     * 作业名称
     */
    @NotBlank(message = "作业名称不能为空！")
    private String name;

    /**
     * 分类目录
     */
    @NotNull(message = "分类目录不能为空！")
    private String dirId;

    /**
     * 描述
     */
    private String description;

    /**
     * 调度_规则信息
     */
    @Valid
    private DctSchedulerRulesVO schedulerRules;

    /**
     * 调度_配置信息
     */
    @Valid
    private DctSchedulerConfigVO schedulerConfig;

    /**
     * 修改时间
     */
    private String updateTime;


    public static DctSchedulerBasicInfoVO getDctSchedulerBasicInfoVO(Object resultData) {
        try {
            Map<String, Object> data = (Map<String, Object>) resultData;
            //封装dctSchedulerBasicInfoVO
            Map<String, Object> processDefinition = (Map<String, Object>) data.get("processDefinition");
            DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO = BeanMapUtils.changeMapToBean(processDefinition, new DctSchedulerBasicInfoVO());
            List<Map<String, Object>> taskDefinitionList = (List<Map<String, Object>>) data.get("taskDefinitionList");
            Map<String, Object> taskParams = (Map<String, Object>) taskDefinitionList.get(taskDefinitionList.size()-1).get("taskParams");
            List<Map<String, Object>> httpParams = (List<Map<String, Object>>) taskParams.get("httpParams");
            for (Map<String, Object> httpParam : httpParams) {
                String value = httpParam.get("value").toString();
                switch (httpParam.get("prop").toString()) {
                    case "schedulerRules":
                        dctSchedulerBasicInfoVO.setSchedulerRules(
                                BeanMapUtils.changeMapToBean(
                                        (Map<String, Object>) GsonUtil.fromJsonString(value),
                                        new DctSchedulerRulesVO()));
                        break;
                    case "schedulerConfig":
                        dctSchedulerBasicInfoVO.setSchedulerConfig(
                                BeanMapUtils.changeMapToBean(
                                        (Map<String, Object>) GsonUtil.fromJsonString(value),
                                        new DctSchedulerConfigVO()));
                        break;
                    case "dirId":
                        dctSchedulerBasicInfoVO.setDirId(value);
                        break;
                }
            }
            return dctSchedulerBasicInfoVO;
        } catch (Exception a) {
            throw new BizException("dolphin detail error :" + a.getMessage());
        }
    }
}
