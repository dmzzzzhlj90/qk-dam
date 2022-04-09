package com.qk.dm.dataingestion.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataxContent {
    private DataxChannel reader;
    private DataxChannel  writer;
}
