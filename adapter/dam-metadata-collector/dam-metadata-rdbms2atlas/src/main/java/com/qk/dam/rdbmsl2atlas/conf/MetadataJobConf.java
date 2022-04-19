package com.qk.dam.rdbmsl2atlas.conf;

import cn.hutool.core.io.resource.ResourceUtil;
import com.qk.dam.rdbmsl2atlas.pojo.MetadataJobYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.MetadataYamlVO;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

/** @author daomingzhu */
public class MetadataJobConf {
  private static final Logger LOG = LoggerFactory.getLogger(MetadataJobConf.class);
  static {
    String filePath = System.getProperty("extractor.conf");
    LOG.info("filePath:"+filePath);
    if (filePath!=null){
      metadataConf =
              new Yaml().loadAs(ResourceUtil.getStream(filePath+"/atlas-extractor-task.yml"), MetadataYamlVO.class);
    }else{
      metadataConf =
              new Yaml().loadAs(ResourceUtil.getStream("atlas-extractor-task.yml"), MetadataYamlVO.class);
    }

  }

  static final MetadataYamlVO metadataConf;

  private MetadataJobConf() {
    throw new IllegalStateException("Utility class");
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
