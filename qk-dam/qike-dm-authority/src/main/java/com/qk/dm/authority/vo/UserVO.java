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
    protected String id;
    protected Long createdTimestamp;
    protected String username;
    protected Boolean enabled;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
}
