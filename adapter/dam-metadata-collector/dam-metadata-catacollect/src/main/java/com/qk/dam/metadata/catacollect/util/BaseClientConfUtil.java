package com.qk.dam.metadata.catacollect.util;

import org.apache.atlas.AtlasClientV2;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zys
 * @date 2022/5/7 17:29
 * @since 1.0.0
 */
public class BaseClientConfUtil {
  private static final Logger LOG = LoggerFactory.getLogger(BaseClientConfUtil.class);
  public static AtlasClientV2 getAtalsV2(String atalsServer, String auth){
    String[] urls = getUrl(atalsServer);
    String[] basicAuthUsernamePassword = getAuth(auth);
     return  new AtlasClientV2(urls, basicAuthUsernamePassword);
  }

  private static String[] getAuth(String auth) {
    if (StringUtils.isNotEmpty(auth)){
      return auth.split(",");
    }else {
      LOG.error("atals的auth为空");
      return null;
    }
  }

  private static String[] getUrl(String atalsServer) {
    if (StringUtils.isNotEmpty(atalsServer)){
      return new String[]{atalsServer};
    }else {
      LOG.error("atals的atalsServer为空");
      return null;
    }
  }
}