/*
package com.qk.dam.metadata.catacollect.base;

import org.apache.atlas.ApplicationProperties;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

*/
/**
 * atlas rest client 配置
 *//*

@Component
public class BaseClientConf {
  protected static final String ATLAS_REST_ADDRESS = "http://172.21.32.2:21000/";
  protected static final String ATLAS_REST_AUTH = "admin,admin";
  public final AtlasClientV2 atlasClientV2;
  private static String[] basicAuthUsernamePassword;

  static {
    try {
      basicAuthUsernamePassword = getBasicAuthenticationInput();
    } catch (AtlasException e) {
      e.printStackTrace();
    }
  }

  protected BaseClientConf() throws AtlasException {
    String[] urls = getServerUrl();
    atlasClientV2 = new AtlasClientV2(urls, basicAuthUsernamePassword);
  }

  protected static String[] getServerUrl() throws AtlasException {

    Configuration configuration = ApplicationProperties.get();
    String[] urls = configuration.getStringArray(ATLAS_REST_ADDRESS);

    if (ArrayUtils.isEmpty(urls)) {
      System.exit(-1);
    }

    return urls;
  }

  protected static String[] getBasicAuthenticationInput() throws AtlasException {
    Configuration configuration = ApplicationProperties.get();
    return configuration.getStringArray(ATLAS_REST_AUTH);
  }

  protected void closeConnection() {
    atlasClientV2.close();
  }
}
*/
