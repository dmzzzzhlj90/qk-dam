package com.qk.dam.authority.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2022/2/24 11:32
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientVO {
    /**
     * id，客户端表id
     */
    private String id;
    /**
     * 唯一标识，客户端id
     */
    private String clientId;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 启用状态
     */
    private Boolean enabled;
    private String baseUrl;
}
