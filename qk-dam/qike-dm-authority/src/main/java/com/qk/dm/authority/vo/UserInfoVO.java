package com.qk.dm.authority.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author shenpj
 * @date 2022/2/22 11:54
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    private String id;
    private Long createdTimestamp;
    private String username;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    //属性
    private Map<String, List<String>> attributes;
    //角色
    private List<RoleVO> clientRoleList;
    //组
    private List<GroupVO> groupList;
}
