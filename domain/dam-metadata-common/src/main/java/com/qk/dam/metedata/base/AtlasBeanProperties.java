package com.qk.dam.metedata.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhudaoming
 */
@Component
@ConfigurationProperties("atlas.rest")
@Data
public class AtlasBeanProperties {
    private String address;

    private String basicAuth;

}