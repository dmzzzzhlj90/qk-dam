package com.qk.dam.rdbmsl2atlas.conf;

import cn.hutool.core.io.resource.ResourceUtil;
import com.qk.dam.rdbmsl2atlas.pojo.MetadataJobYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.MetadataYamlVO;
import java.util.Objects;
import org.yaml.snakeyaml.Yaml;

/** @author daomingzhu */
public class MetadataJobConf {
  private static MetadataYamlVO metadataConf;

  static {
    metadataConf =
        new Yaml().loadAs(ResourceUtil.getStream("atlas-extractor-task.yml"), MetadataYamlVO.class);
  }

  public static MetadataJobYamlVO getMetadataJobYamlVO(String jobName) {
    MetadataJobYamlVO yamlVO =
        metadataConf.getMetadata().stream()
            .filter(metadataJobYamlVO -> jobName.equals(metadataJobYamlVO.getName()))
            .findFirst()
            .orElse(null);
    Objects.requireNonNull(yamlVO, jobName + "获取的配置不存在");
    return yamlVO;
  }
}
