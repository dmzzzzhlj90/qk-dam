
package com.qk.dm.dataquality.dolphinapi.dto;
import lombok.Data;

import java.util.List;


@Data
public class ConditionResultDTO {
    private List<String> successNode;
    private List<String> failedNode;

}