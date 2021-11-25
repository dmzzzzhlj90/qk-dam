package com.qk.dm.dataquality.dolphinapi.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.dolphinapi.service.ScheduleApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author shenpj
 * @date 2021/11/25 2:20 下午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/schedule")
public class ScheduleApiController {
  private final ScheduleApiService scheduleApiService;

  public ScheduleApiController(ScheduleApiService scheduleApiService) {
    this.scheduleApiService = scheduleApiService;
  }

  @PostMapping("")
  public DefaultCommonResult save(
      @RequestBody @Validated DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    scheduleApiService.create(dqcSchedulerConfigVO);
    return DefaultCommonResult.success();
  }

  @PutMapping("/{id}")
  public DefaultCommonResult update(
      @PathVariable("id") Integer id,
      @RequestBody @Validated DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    scheduleApiService.update(id, dqcSchedulerConfigVO);
    return DefaultCommonResult.success();
  }

  @PutMapping("/online/{id}")
  public DefaultCommonResult online(@PathVariable("id") Integer id) {
    scheduleApiService.online(id);
    return DefaultCommonResult.success();
  }

  @PutMapping("/offline/{id}")
  public DefaultCommonResult offline(@PathVariable("id") Integer id) {
    scheduleApiService.offline(id);
    return DefaultCommonResult.success();
  }

  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteOne(@PathVariable("id") Integer id) {
    scheduleApiService.deleteOne(id);
    return DefaultCommonResult.success();
  }

  @GetMapping("")
  public DefaultCommonResult search(Integer processDefinitionId, Integer pageNo, Integer pageSize) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, scheduleApiService.search(processDefinitionId, 1, 1, null));
  }
}
