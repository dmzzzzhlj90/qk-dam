package com.qk.dm.dataservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * API基础信息VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiBasicInfoVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * API标识ID
     */
    private String apiId;

    /**
     * API目录ID
     */
    @NotBlank(message = "API目录ID不能为空！")
    private String dirId;

    /**
     * API目录名称
     */
    @NotBlank(message = "API目录名称不能为空！")
    private String dirName;

    /**
     * API名称
     */
    @NotBlank(message = "API名称不能为空！")
    private String apiName;

    /**
     * 请求Path
     */
    @NotBlank(message = "请求Path不能为空！")
    private String apiPath;

    /**
     * 请求协议
     */
    @NotBlank(message = "请求协议不能为空！")
    private String protocolType;

    /**
     * 请求方法
     */
    @NotBlank(message = "请求方法不能为空！")
    private String requestType;

    /**
     * API类型
     */
    private String apiType;

    /**
     * 同步状态; 0: 新建未上传数据网关; 1: 上传数据网关失败; 2: 成功上传数据网关;
     */
    private String status;

    /**
     * 入参定义
     */
    private List<DasApiBasicInfoRequestParasVO> apiBasicInfoRequestParasVOS;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    private String createUserid;

    /**
     * 修改人
     */
    private String updateUserid;

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
}
