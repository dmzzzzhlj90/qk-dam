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
public class AtyGroupVO {
    /**
     * 域
     */
    @NotBlank(message = "域必填！")
    String realm;
    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称必填！")
    String groupName;
}
