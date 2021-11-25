package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProcessDataDTO {
    /**
     * task list
     */
    private List<TaskNodeDTO> tasks;

    /**
     * global parameters
     */
    private List<PropertyDTO> globalParams;


    private int timeout;

    private int tenantId;

}