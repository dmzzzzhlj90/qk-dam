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
    private String username;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
