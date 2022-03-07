package com.qk.dm.authority.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 14:17
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyGroupBatchByUsersVO {
    /**
     * 域
     */
    @NotNull
    String realm;
    /**
     * 用户列表
     */
    @NotEmpty
    List<String> userIds;
    /**
     * 用户组
     */
    @NotNull
    String groupId;
}
