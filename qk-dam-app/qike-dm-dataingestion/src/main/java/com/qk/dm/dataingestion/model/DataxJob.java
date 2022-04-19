package com.qk.dm.dataingestion.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DataxJob {
    private DataxSetting setting;
    private List<DataxContent> content;
}
