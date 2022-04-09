package com.qk.dam.dataservice.spi.server;

import java.util.List;
import java.util.Map;

public interface ServerService {

  List<Map<String,String>> getServerInfo();

  List apiSixServiceInfoIds();

}
