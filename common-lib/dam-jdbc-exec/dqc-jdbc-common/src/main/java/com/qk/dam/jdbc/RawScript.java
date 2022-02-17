package com.qk.dam.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MYSQL脚本执行参数信息
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawScript {

    private String from_host;

    private String from_port;

    private String from_user;

    private String from_password;

    private String from_database;

    private String to_host;

    private String to_port;

    private String to_user;

    private String to_password;

    private String to_database;

    private String job_id;

    private String job_name;

    private String rule_id;

    private String rule_name;

    private String rule_meta_data;

    private Long rule_temp_id;

    private Long task_code;

    private String sql_rpc_url;

    private String warn_rpc_url;

}