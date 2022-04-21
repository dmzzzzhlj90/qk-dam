package com.qk.dm.dataingestion.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisAttrViewVO {

    /**
     * 字段前端显示名称
     */
    private String title;

    /**
     * 数据库类型 如 mysql  hive
     */
    @JsonIgnore
    private String connectType;

    /**
     * 前端请求后端接口传递的参数名称
     */
    private String dataIndex;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 前端展示的数据类型  例如 text、select
     */
    private String valueType;

    private Map<String,String> valueEnum;

    /**
     * 默认值
     */
    private String initialValue = Strings.EMPTY;

}
