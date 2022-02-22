package com.qk.dm.authority.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2022/2/22 11:54
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String id;
    private Long createdTimestamp;
    private String username;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
