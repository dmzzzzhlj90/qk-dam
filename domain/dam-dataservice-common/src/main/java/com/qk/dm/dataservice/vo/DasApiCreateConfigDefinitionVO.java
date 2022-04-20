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
 * 新建API_配置方式_配置信息定义类
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
public class DasApiCreateConfigDefinitionVO {

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
     * 取数据方式
     */
    @ExcelProperty(value = {"取数据方式"}, index = 1)
    @NotBlank(message = "取数据方式不能为空！")
    private String accessMethod;

    /**
     * 数据源连接类型
     */
    @ExcelProperty(value = {"数据源连接类型"}, index = 2)
    @NotBlank(message = "数据源类型不能为空！")
    private String connectType;

    /**
     * 数据源连接名称
     */
    @ExcelProperty(value = {"数据源连接名称"}, index = 3)
    @NotBlank(message = "数据源连接不能为空！")
    private String dataSourceName;

    /**
     * 数据库
     */
    @ExcelProperty(value = {"数据库"}, index = 4)
    @NotBlank(message = "数据库不能为空！")
    private String dataBaseName;

    /**
     * 数据表
     */
    @ExcelProperty(value = {"数据表"}, index = 5)
    @NotBlank(message = "数据表不能为空！")
    private String tableName;

    /**
     * 请求参数
     */
    @ExcelIgnore
    @ExcelProperty(value = {"请求参数"}, index = 6)
    private List<DasApiCreateRequestParasVO> apiCreateRequestParasVOS;

    /**
     * 请求参数JSON
     */
    @ExcelProperty(value = {"请求参数JSON"}, index = 6)
    private String createRequestParasJson;

    /**
     * 响应参数
     */
    @ExcelIgnore
    @ExcelProperty(value = {"响应参数"}, index = 7)
    private List<DasApiCreateResponseParasVO> apiCreateResponseParasVOS;

    /**
     * 响应参数JSON
     */
    @ExcelProperty(value = {"响应参数JSON"}, index = 7)
    private String createResponseParasJson;

    /**
     * 排序参数
     */
    @ExcelIgnore
    @ExcelProperty(value = {"排序参数"}, index = 8)
    private List<DasApiCreateOrderParasVO> apiCreateOrderParasVOS;

    /**
     * 排序参数JSON
     */
    @ExcelProperty(value = {"排序参数JSON"}, index = 8)
    private String createOrderParasJson;

    /**
     * 描述
     */
    @ExcelProperty(value = {"描述"}, index = 9)
    private String description;
}