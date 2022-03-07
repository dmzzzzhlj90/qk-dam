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
    /**
     * 域
     */
    @NotNull
    String realm;
    /**
     * 用户id
     */
    @NotNull
    String userId;
    /**
     * 分组id
     */
    @NotNull
    String groupId;
}
