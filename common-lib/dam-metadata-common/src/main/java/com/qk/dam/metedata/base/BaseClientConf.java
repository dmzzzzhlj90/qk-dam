package com.qk.dam.metedata.base;

import com.qk.dam.metedata.util.SpringApplicationContextHolder;
import org.apache.atlas.ApplicationProperties;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.ArrayUtils;

/**
 * atlas rest client 配置
 *
 * @author daomingzhu
 */
@Deprecated(since = "20220329",forRemoval = true)
public abstract class BaseClientConf {
  protected static final String ATLAS_REST_ADDRESS = "atlas.rest.address";
  protected static final String ATLAS_REST_AUTH = "atlas.rest.basicAuth";
  public static AtlasClientV2 atlasClientV2 = null;
  private static String[] basicAuthUsernamePassword;

  static {
    try {
      basicAuthUsernamePassword = getBasicAuthenticationInput();
      String[] urls = getServerUrl();
      atlasClientV2 = new AtlasClientV2(urls, basicAuthUsernamePassword);
    } catch (AtlasException e) {
      e.printStackTrace();
    }
  }

  protected BaseClientConf() throws AtlasException {

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
