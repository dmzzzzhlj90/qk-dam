package com.qk.dam.hive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultTable {
    private String job_id;
    private String job_name;
    private String rule_id;
    private String rule_name;
    private Long rule_temp_id;
    private Long task_code;
    private String rule_result;
    private String del_flag;
    private Date gmt_create;
    private Date gmt_modified;
}
