package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataPushVO {

  /** 查询条件Id */
  @NotBlank(message = "查询条件ID不能为空！")
  private String id;

  /** 查询条件名称 */
  private String name;

  /** 查询条件参数 */
  @NotBlank(message = "查询条件不能为空！")
  private String parameter;

  /** 父级id */
  //    @Valid
  //    @NotEmpty(message = "查询结果集合不能为空！")
  private List<DataPushDetailListVO> dataPushDetailList;
}
