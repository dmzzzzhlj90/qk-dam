package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DataService;
import com.qk.dm.dataservice.vo.DataPushVO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
  public DefaultCommonResult dataPush(@RequestBody @Valid DataPushVO dataPushVO) {
    return new DefaultCommonResult(ResultCodeEnum.OK, dataService.dataPush(dataPushVO));
  }
}
