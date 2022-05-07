package com.qk.dam.metadata.catacollect.util;

import org.apache.atlas.ApplicationProperties;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author zys
 * @date 2022/5/7 17:29
 * @since 1.0.0
 */
public class BaseClientConfUtil {
  public static AtlasClientV2 getAtalsV2(String atalsServer, String auth)
      throws AtlasException {
    String[] urls = getServerUrl(atalsServer);
    String[] basicAuthUsernamePassword = getBasicAuthenticationInput(auth);
     return  new AtlasClientV2(urls, basicAuthUsernamePassword);
  }

  private static String[] getBasicAuthenticationInput(String auth)
      throws AtlasException {
    Configuration configuration = ApplicationProperties.get();
    return configuration.getStringArray(auth);
  }

  private static String[] getServerUrl(String atalsServer)
      throws AtlasException {
    Configuration configuration = ApplicationProperties.get();
    String[] urls = configuration.getStringArray(atalsServer);

    if (ArrayUtils.isEmpty(urls)) {
      System.exit(-1);
    }
    return urls;
  }
}