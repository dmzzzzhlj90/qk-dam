package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MYSQL脚本执行参数信息
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class MysqlRawScript {

    private String from_host;

    private String from_user;

    private String from_password;

    private String from_database;

//    private String search_sql;

    private String to_host;

    private String to_user;

    private String to_password;

    private String to_database;

    private String job_id;

    private String job_name;

    private String rule_id;

    private String rule_name;

    private Long rule_temp_id;

    private Long task_code;

    private String rule_meta_data;

    private String sql_rpc_url;

}