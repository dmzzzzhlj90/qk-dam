package com.qk.dam.metadata.catacollect.util;

import com.qk.dam.commons.exception.BizException;
import org.apache.atlas.AtlasClientV2;
import org.apache.commons.lang.StringUtils;

/**
 * @author zys
 * @date 2022/5/7 17:29
 * @since 1.0.0
 */
public class BaseClientConfUtil {
  public static AtlasClientV2 getAtalsV2(String atalsServer, String auth){
    String[] urls = getUrl(atalsServer);
    String[] basicAuthUsernamePassword = getAuth(auth);
     return  new AtlasClientV2(urls, basicAuthUsernamePassword);
  }

  private static String[] getAuth(String auth) {
    if (StringUtils.isNotEmpty(auth)){
      return auth.split(",");
    }else {
      throw  new BizException("atals的auth为空");
    }
  }

  private static String[] getUrl(String atalsServer) {
    if (StringUtils.isNotEmpty(atalsServer)){
      return new String[]{atalsServer};
    }else {
      throw new BizException("atals的atalsServer为空");
    }
  }
}