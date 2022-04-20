package com.qk.dm.authority.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author shenpj
 * @date 2022/3/2 17:56
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyGroupDetailVO {
    /**
     * 分组id
     */
    @NotBlank(message = "id必填！")
    String groupId;
    /**
     * 域
     */
    @NotBlank(message = "域必填！")
    String realm;
}
