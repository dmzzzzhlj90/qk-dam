package com.qk.dm.datacollect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/26 2:58 下午
 * @since 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskInstanceListResultDTO implements Serializable {
    private String processInstanceState;
    private List<TaskInstanceListDTO> taskList;
}
