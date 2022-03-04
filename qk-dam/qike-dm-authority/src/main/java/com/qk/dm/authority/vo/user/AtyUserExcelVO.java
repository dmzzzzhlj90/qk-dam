package com.qk.dm.authority.vo.user;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户列表导出excelVO
 * @author zys
 * @date 2022/3/4 14:01
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class AtyUserExcelVO {
  @ExcelProperty(value = "用户ID",order = 1)
  private String id;
  @ExcelProperty(value = "注册时间",order = 6)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createdTime;
  @ExcelProperty(value = "用户名称",order = 2)
  private String username;
  @ExcelProperty(value = "是否启动",order = 7)
  private Boolean enabled;
  @ExcelProperty(value = "用户名",order = 4)
  private String firstName;
  @ExcelProperty(value = "用户姓",order = 5)
  private String lastName;
  @ExcelProperty(value = "信箱",order = 3)
  private String email;
  @ExcelIgnore
  private String password;
  //属性
  @ExcelIgnore
  private Map<String,List<String>> attributes;
  //角色
  //    private List<RoleVO> clientRoleList;
  //组
  @ExcelIgnore
  private List<AtyGroupInfoVO> groupList;
}