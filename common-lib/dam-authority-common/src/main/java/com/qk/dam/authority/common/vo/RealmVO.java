package com.qk.dam.authority.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2022/2/24 11:24
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealmVO {
    private String id;
    /**
     * 域
     */
    private String realm;
    /**
     * 描述
     */
    private String displayName;
    private String displayNameHtml;
}
