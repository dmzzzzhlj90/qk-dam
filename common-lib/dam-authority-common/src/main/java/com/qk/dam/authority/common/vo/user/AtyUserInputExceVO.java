package com.qk.dam.authority.common.vo.user;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 用户导入VO
 * @author zys
 * @date 2022/3/4 14:55
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class AtyUserInputExceVO {
  @ExcelProperty(value = "用户名称(必填)",order = 1)
  @NotNull(message = "用户名必填!")
  private String username;
  @ExcelProperty(value = "用户密码(必填)",order = 2)
  @NotNull(message = "密码必填!")
  private String password;
  @ExcelProperty(value = "启用状态(必填)",order = 4)
  @NotNull(message = "启用状态必填!")
  private Boolean enabled;
  @ExcelProperty(value = "用户名",order = 5)
  private String firstName;
  @ExcelProperty(value = "用户姓",order = 6)
  private String lastName;
  @ExcelProperty(value = "电子邮件",order = 7)
  @Email
  private String email;
}