package com.qk.dam.metedata;

import com.qk.dam.metedata.base.AtlasBeanProperties;
import org.apache.atlas.AtlasClientV2;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
 * 注入的方式获取AtlasClient sdk
 *
 * @author zhudaoming
 * @date 2022 03 21
 */
@Component
public class AtlasClient  {

    private AtlasClientV2 atlasClientV2;
    private final AtlasBeanProperties atlasBeanProperties;

    public AtlasClient(AtlasBeanProperties atlasBeanProperties) {
        this.atlasBeanProperties = atlasBeanProperties;
    }

    @PostConstruct
    public void init(){
        atlasClientV2 = new AtlasClientV2(new String[]{atlasBeanProperties.getAddress()},
                atlasBeanProperties.getBasicAuth().split(","));
    }

    public AtlasClientV2 instance() {
        return atlasClientV2;
    }

}
