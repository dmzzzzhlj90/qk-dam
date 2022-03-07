package com.qk.dm.authority.vo.user;

import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 用户信息
 * @author shenpj
 * @date 2022/2/22 11:54
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyUserInfoVO {
    private String id;
    private String createdTimestamp;
    private String username;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    //属性
    private Map<String, List<String>> attributes;
    //角色
//    private List<RoleVO> clientRoleList;
    //组
    private List<AtyGroupInfoVO> groupList;
}
