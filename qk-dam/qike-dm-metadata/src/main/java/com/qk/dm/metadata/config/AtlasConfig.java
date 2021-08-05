package com.qk.dm.metadata.config;

import com.qk.dam.rdbmsl2atlas.base.BaseClientConf;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasException;

/**
 * @author spj
 * @date 2021/8/2 11:38 上午
 * @since 1.0.0
 */
public class AtlasConfig extends BaseClientConf {
  protected AtlasConfig() throws AtlasException {
    super();
  }

  private static AtlasConfig instance;

  static {
    try {
      instance = new AtlasConfig();
    } catch (AtlasException e) {
      e.printStackTrace();
    }
  }

  public static AtlasClientV2 getAtlasClientV2() {
    return instance.atlasClientV2;
  }
}
