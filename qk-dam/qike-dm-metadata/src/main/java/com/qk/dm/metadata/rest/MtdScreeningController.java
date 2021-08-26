package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdScreeningService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 元数据列表筛选条件功能
 *
 * @author shenpj
 * @date 2021/8/17 11:21 上午
 * @since 1.0.0
 */
@RestController
//@RequestMapping("/screening")
public class MtdScreeningController {

  private final MtdScreeningService mtdScreeningService;

  public MtdScreeningController(MtdScreeningService mtdScreeningService) {
    this.mtdScreeningService = mtdScreeningService;
  }

  /**
   * 查询筛选条件列表
   *
   * @return DefaultCommonResult<Map<String, List<String>>>
   */
//  @GetMapping("/list")
  public DefaultCommonResult<Map<String, List<String>>> screeningList() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, mtdScreeningService.screeningList());
  }
}
