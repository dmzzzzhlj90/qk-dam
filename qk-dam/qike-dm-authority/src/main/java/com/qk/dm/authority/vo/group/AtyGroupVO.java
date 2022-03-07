package com.qk.dm.authority.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2022/3/2 17:56
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyGroupVO {
    String id;
    /**
     * 域
     */
    @NotNull
    String realm;
    /**
     * 分组名称
     */
    String groupName;
}
