package com.qk.dm.dataservice.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DasApiBasicInfoVO {

    /**
     * 主键ID
     */
    @ExcelIgnore
    @ExcelProperty(value = {"主键ID"}, index = 0)
    private Long id;

    /**
     * API标识ID
     */
    @ExcelProperty(value = {"API标识ID"}, index = 0)
    private String apiId;

    /**
     * API目录ID
     */
    @ExcelProperty(value = {"API目录ID"}, index = 1)
    @NotBlank(message = "API目录ID不能为空！")
    private String dirId;

    /**
     * API目录名称
     */
    @ExcelProperty(value = {"API目录名称"}, index = 2)
    @NotBlank(message = "API目录名称不能为空！")
    private String dirName;

    /**
     * API名称
     */
    @ExcelProperty(value = {"API名称"}, index = 3)
    @NotBlank(message = "API名称不能为空！")
    private String apiName;

    /**
     * 请求Path
     */
    @ExcelProperty(value = {"请求Path"}, index = 4)
    @NotBlank(message = "请求Path不能为空！")
    private String apiPath;

    /**
     * 请求协议
     */
    @ExcelProperty(value = {"请求协议"}, index = 5)
    @NotBlank(message = "请求协议不能为空！")
    private String protocolType;

    /**
     * 请求方法
     */
    @ExcelProperty(value = {"请求方法"}, index = 6)
    @NotBlank(message = "请求方法不能为空！")
    private String requestType;

    /**
     * API类型
     */
    @ExcelProperty(value = {"API类型"}, index = 7)
    private String apiType;

    /**
     * 同步状态; 0: 新建未上传数据网关; 1: 上传数据网关失败; 2: 成功上传数据网关;
     */
    @ExcelProperty(value = {"同步状态"}, index = 8)
    private String status;

    /**
     * 入参定义
     */
    @ExcelIgnore
    @ExcelProperty(value = {"入参定义"}, index = 9)
    private List<DasApiBasicInfoRequestParasVO> apiBasicInfoRequestParasVOS;


    /**
     * 结果集数据格式类型: 0单条,1列表
     */
    @ExcelProperty(value = {"结果集数据格式类型"}, index = 2)
    @NotNull(message = "结果集数据格式类型不能为空！")
    private Integer resultDataType;

    /**
     * 入参定义 JSON
     */
    @ExcelProperty(value = {"入参定义 JSON"}, index = 9)
    private String requestParasJSON;

    /**
     * 描述
     */
    @ExcelProperty(value = {"描述"}, index = 10)
    private String description;

    /**
     * 创建人
     */
    @ExcelIgnore
    private String createUserid;

    /**
     * 修改人
     */
    @ExcelIgnore
    private String updateUserid;

    /**
     * 创建时间
     */
    @ExcelIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ExcelIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;
}
