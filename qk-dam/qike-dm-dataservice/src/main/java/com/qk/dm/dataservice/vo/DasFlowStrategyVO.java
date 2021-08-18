package com.qk.dm.dataservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zys
 * @date 2021/8/18 10:26
 * @since 1.0.0 数据服务-服务流控
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasFlowStrategyVO {
    /** 主键ID */
    private Long id;

    /** API关联ID */
    private String apiId;

    /** 策略名称 */
    private String strategyName;

    /** 时长 */
    private String timeValue;

    /** 时间单位 */
    private String timeUnit;

    /** API流量限制 */
    private String apiTimes;

    /** 用户流量限制 */
    private String userTimes;

    /** 应用流量限制 */
    private String appTimes;

    /** 源IP流量限制 */
    private String ipTimes;

    /** 描述 */
    private String description;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /** 修改时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /** 是否删除；0逻辑删除，1物理删除； */
    private Integer delFlag;
}