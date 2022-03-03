package com.qk.dm.authority.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2022/3/3 14:17
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyUserGroupVO {
    @NotNull
    String realm;
    @NotNull
    String userId;
    @NotNull
    String groupId;
}
