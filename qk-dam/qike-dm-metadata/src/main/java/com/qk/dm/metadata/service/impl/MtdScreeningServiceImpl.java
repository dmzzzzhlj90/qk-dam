package com.qk.dm.metadata.service.impl;

import com.qk.dm.metadata.feign.DataSourceFeign;
import com.qk.dm.metadata.service.MtdScreeningService;
import com.qk.dm.metadata.vo.MtdClassifyInfoVO;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/8/17 11:23 上午
 * @since 1.0.0
 */
@Service
public class MtdScreeningServiceImpl implements MtdScreeningService {

  private final MtdLabelsServiceImpl mtdLabelsService;
  private final MtdClassifyServiceImpl mtdClassifyService;
  private final DataSourceFeign dataSourceFeign;

  public MtdScreeningServiceImpl(
      MtdLabelsServiceImpl mtdLabelsService,
      MtdClassifyServiceImpl mtdClassifyService,
      DataSourceFeign dataSourceFeign) {
    this.mtdLabelsService = mtdLabelsService;
    this.mtdClassifyService = mtdClassifyService;
    this.dataSourceFeign = dataSourceFeign;
  }

  @Override
  public Map<String, List<String>> screeningList() {
    ResponseEntity<JSONObject> getdatatype = dataSourceFeign.getdatatype();
    List<MtdLabelsInfoVO> mtdLabelsInfoVOS = mtdLabelsService.listByAll(new MtdLabelsVO());
    List<MtdClassifyInfoVO> mtdClassifyInfoVOS = mtdClassifyService.listByAll(new MtdClassifyVO());
    Map<String, List<String>> map = new LinkedHashMap<>();
    map.put("datasource", (List<String>) getdatatype.getBody().get("data"));
    map.put(
        "classify",
        mtdClassifyInfoVOS.stream().map(MtdClassifyInfoVO::getName).collect(Collectors.toList()));
    map.put(
        "labels",
        mtdLabelsInfoVOS.stream().map(MtdLabelsInfoVO::getName).collect(Collectors.toList()));
    return map;
  }
}
