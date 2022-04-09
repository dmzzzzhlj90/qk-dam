package com.qk.dam.metedata.config;

import com.qk.dam.metedata.AtlasClient;
import com.qk.dam.metedata.base.BaseClientConf;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author spj
 * @date 2021/8/2 11:38 上午
 * @since 1.0.0
 */
@Component
@Deprecated(since = "20220329",forRemoval = true)
public class AtlasConfig extends BaseClientConf {

  @Autowired
  private AtlasClient atlasClient;

  protected AtlasConfig() throws AtlasException {
  }


  @PostConstruct
  public void init(){
      if (Objects.nonNull(atlasClient)){
        ac.set(atlasClient.instance());
      }
  }

  private static final AtomicReference<AtlasClientV2> ac = new AtomicReference<>();

  public static AtlasClientV2 getAtlasClientV2() {
    if (Objects.nonNull(ac.get())){
      return ac.get();
    }
    return atlasClientV2;
  }
}
