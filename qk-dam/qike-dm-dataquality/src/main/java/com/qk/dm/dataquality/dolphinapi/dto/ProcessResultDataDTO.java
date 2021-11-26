
package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProcessResultDataDTO implements Serializable {

    private List<ProcessDefinitionDTO> totalList;

    private int total;

    private int currentPage;

    private int totalPage;

}