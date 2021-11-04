package com.qk.dam.rdbmsl2atlas.pojo;

/** @author daomingzhu */
public class ServerinfoYamlVO {
  private String applicationName;
  private String owner;
  private String description;
  private String displayName;

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return "ServerinfoYamlVO{"
        + "applicationName='"
        + applicationName
        + '\''
        + ", owner='"
        + owner
        + '\''
        + ", description='"
        + description
        + '\''
        + ", displayName='"
        + displayName
        + '\''
        + '}';
  }
}
