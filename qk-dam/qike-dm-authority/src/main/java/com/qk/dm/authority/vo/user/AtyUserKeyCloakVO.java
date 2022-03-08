package com.qk.dm.authority.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2022/3/2 15:01
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyUserKeyCloakVO {
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 开启状态
     */
    private Boolean enabled;
    /**
     * 名字
     */
    private String firstName;
    /**
     * 姓氏
     */
    private String lastName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
}
