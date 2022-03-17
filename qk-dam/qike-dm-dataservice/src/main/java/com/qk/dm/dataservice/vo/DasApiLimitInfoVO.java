package com.qk.dm.dataservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 数据服务_服务流控管理VO
 *
 * @author wjq
 * @date 2022/3/16
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiLimitInfoVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 策略名称
     */
    @NotBlank(message = "策略名称不能为空！")
    private String limitName;

    /**
     * 时长
     */
    @NotBlank(message = "时长不能为空！")
    private Integer limitTime;

    /**
     * API流量限制(次)
     */
    @NotBlank(message = "API流量限制(次)不能为空！")
    private Integer apiLimitCount;

    /**
     * 用户流量限制(次)
     */
    private Integer userLimitCount;

    /**
     * 应用流量限制(次)
     */
    private Integer appLimitCount;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 是否删除；0逻辑删除，1物理删除；
     */
    private Integer delFlag;

}
