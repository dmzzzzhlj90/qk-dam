package com.qk.dam.rdbmsl2atlas.base;

import org.apache.atlas.ApplicationProperties;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.ArrayUtils;

public abstract class BaseClientConf {
  protected static final String ATLAS_REST_ADDRESS = "atlas.rest.address";
  protected static final String ATLAS_REST_AUTH = "atlas.rest.basic.auth";
  public AtlasClientV2 atlasClientV2;
  private static String[] basicAuthUsernamePassword = null;

  protected BaseClientConf() throws AtlasException {
    String[] urls = getServerUrl();
    basicAuthUsernamePassword = getBasicAuthenticationInput();
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
    String[] basicAuthUsernamePassword = configuration.getStringArray(ATLAS_REST_AUTH);

    return basicAuthUsernamePassword;
  }

  protected void closeConnection() {
    atlasClientV2.close();
  }
}
