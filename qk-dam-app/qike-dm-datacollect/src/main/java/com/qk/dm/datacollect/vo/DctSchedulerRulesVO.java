package com.qk.dm.datacollect.vo;

import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.dam.commons.util.GsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 数据质量_规则调度_规则信息VO
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DctSchedulerRulesVO {

    /**
     * 适用引擎 MYSQL HIVE ORACLE
     */
    @NotBlank(message = "适用引擎不能为空！")
    private String engineType;

    /**
     * 数据连接
     */
    private String dataSourceName;

    /**
     * 数据库名称
     */
    @NotBlank(message = "数据库名称不能为空！")
    private String databaseName;

    /**
     * 表名称
     */
    private List<String> tableList;

    /**
     * 全选所有的表时候赋值为（all）
     */
    private String allNums;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 创建人名称
     */
    private String owner;

    /**
     * 采集元数据策略（1：仅更新、2：仅添加、3：既更新又添加、4：忽略更新添加）
     */
    @NotBlank(message = "元数据策略不能为空！")
    private String strategy;

    /**
     * 数据源Id
     */
    @NotBlank(message = "数据源Id不能为空！")
    private String dataSourceId;

    /**
     * 描述
     */
    private String description;

    public static DctSchedulerRulesVO jsonStringChangeRules(String value) {
        return BeanMapUtils.changeMapToBean((Map<String, Object>) GsonUtil.fromJsonString(value), new DctSchedulerRulesVO());
    }
}
