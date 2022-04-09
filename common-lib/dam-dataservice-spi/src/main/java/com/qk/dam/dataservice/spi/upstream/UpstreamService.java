package com.qk.dam.dataservice.spi.upstream;

import java.util.List;
import java.util.Map;

public interface UpstreamService {

  List<Map<String,String>> getUpstreamInfo();

  List apiSixUpstreamInfoIds();
}
