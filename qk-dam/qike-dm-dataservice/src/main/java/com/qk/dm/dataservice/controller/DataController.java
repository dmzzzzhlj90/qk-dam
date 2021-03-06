package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DataService;
import com.qk.dm.dataservice.vo.DataPushVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shen
 * @version 1.0
 * @description:
 * @date 2021-07-12 13:55
 */
@RestController
@RequestMapping("/data")
public class DataController {

  @Autowired DataService dataService;

  @PostMapping("/push")
  public DefaultCommonResult<String> dataPush(@RequestBody @Validated DataPushVO dataPushVO) {
    String dataStr = dataService.dataPush(dataPushVO);
    return DefaultCommonResult.success(ResultCodeEnum.OK, dataStr);
  }
}
