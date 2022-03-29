package com.qk.dm.authority.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "域必填！")
    String realm;

    /**
     * 用户列表
     */
    List<String> userIds;

    /**
     * 用户组id
     */
    @NotBlank(message = "用户组id必填！")
    String groupId;
}
