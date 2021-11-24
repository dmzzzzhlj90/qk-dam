package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

@Data
public class TimeOutDTO {

    private String strategy;
    private String interval;
    private boolean enable;
}