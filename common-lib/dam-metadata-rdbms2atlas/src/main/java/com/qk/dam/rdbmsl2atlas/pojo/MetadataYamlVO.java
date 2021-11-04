package com.qk.dam.rdbmsl2atlas.pojo;

import java.util.List;

public class MetadataYamlVO {
  private List<MetadataJobYamlVO> metadata;

  public List<MetadataJobYamlVO> getMetadata() {
    return metadata;
  }

  public void setMetadata(List<MetadataJobYamlVO> metadata) {
    this.metadata = metadata;
  }

  @Override
  public String toString() {
    return "MetadataConf{" + "metadata=" + metadata + '}';
  }
}
