package com.qk.dm.metadata.service.impl;

import com.qk.dam.rdbmsl2atlas.base.BaseClientConf;
import org.apache.atlas.AtlasException;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.discovery.AtlasQuickSearchResult;
import org.apache.atlas.model.instance.AtlasEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spj
 * @date 2021/8/2 10:22 上午
 * @since 1.0.0
 */
public class AtlasTestServiceImpl extends BaseClientConf {

    private static final Logger LOG = LoggerFactory.getLogger(AtlasTestServiceImpl.class);
    protected AtlasTestServiceImpl() throws AtlasException {
        super();
    }

    public static void main(String[] args) throws AtlasException, AtlasServiceException {
        new AtlasTestServiceImpl().quick();
    }

    public void quick()
            throws AtlasServiceException {
        AtlasQuickSearchResult atlasQuickSearchResult = atlasClientV2.quickSearch("sales_fac", null, false, 5, 0);
        LOG.info("调取远程接口==========》",atlasQuickSearchResult);
        System.out.println(atlasQuickSearchResult.toString());
    }

    public void createEntities(AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo)
            throws AtlasServiceException {
        atlasClientV2.createEntities(atlasEntitiesWithExtInfo);
    }
}
