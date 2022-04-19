package com.qk.dm.dataservice.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 注册API_接口定义_对应实体对象VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DasApiRegisterDefinitionVO {

    /**
     * 主键ID
     */
    @ExcelIgnore
    @ExcelProperty(value = {"主键ID"}, index = 0)
    private Long id;

    /**
     * API基础信息ID
     */
    @ExcelProperty(value = {"API基础信息ID"}, index = 0)
    private String apiId;

    /**
     * API路由RouteId
     */
    @ExcelProperty(value = {"API路由RouteId"}, index = 1)
    private String apiRouteId;

    /**
     * 请求协议
     */
    @ExcelProperty(value = {"请求协议"}, index = 2)
    @NotBlank(message = "请求协议不能为空！")
    private String protocolType;

    /**
     * 请求方式
     */
    @ExcelProperty(value = {"请求方式"}, index = 3)
    @NotBlank(message = "请求方式不能为空！")
    private String requestType;

    /**
     * 后端服务 HOST
     */
    @ExcelProperty(value = {"后端服务 HOST"}, index = 4)
    @NotBlank(message = "后端服务 HOST不能为空！")
    private String backendHost;

    /**
     * 后端服务 PATH
     */
    @ExcelProperty(value = {"后端服务 PATH"}, index = 5)
    @NotBlank(message = "后端服务 PATH不能为空！")
    private String backendPath;

    /**
     * 后端超时 (ms)
     */
    @ExcelProperty(value = {"后端超时 (ms)"}, index = 6)
    @NotBlank(message = "后端超时 (ms)不能为空！")
    private String backendTimeout;

    /**
     * 后端服务参数
     */
    @ExcelIgnore
    @ExcelProperty(value = {"后端服务参数"}, index = 7)
    private List<DasApiRegisterBackendParaVO> apiRegisterBackendParaVOS;

    /**
     * 后端服务参数JSON
     */
    @ExcelProperty(value = {"后端服务参数JSON"}, index = 7)
    private String registerBackendParasJson;

    /**
     * 常量参数
     */
    @ExcelIgnore
    @ExcelProperty(value = {"常量参数"}, index = 8)
    private List<DasApiRegisterConstantParaVO> apiRegisterConstantParaVOS;

    /**
     * 常量参数JSON
     */
    @ExcelProperty(value = {"常量参数JSON"}, index = 8)
    private String registerConstantParasJson;

    /**
     * 描述
     */
    @ExcelProperty(value = {"描述"}, index = 9)
    private String description;
}
