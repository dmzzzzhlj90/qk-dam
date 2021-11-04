package com.qk.dm.indicator.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.indicator.common.enums.DateCycleEnum;
import com.qk.dam.indicator.common.enums.FunctionTypeEnum;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcFunctionDTO;
import com.qk.dm.indicator.params.dto.IdcFunctionPageDTO;
import com.qk.dm.indicator.params.vo.IdcFunctionVO;
import com.qk.dm.indicator.service.IdcFunctionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 函数功能
 *
 * @author shenpj
 * @date 2021/9/1 2:47 下午
 * @since 1.0.0
 */
@RestController
@RequestMapping("/function")
public class IdcFunctionController {
  private final IdcFunctionService idcFunctionService;

  public IdcFunctionController(IdcFunctionService idcFunctionService) {
    this.idcFunctionService = idcFunctionService;
  }

  /**
   * 新增
   *
   * @param idcFunctionDTO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody @Validated IdcFunctionDTO idcFunctionDTO) {
    idcFunctionService.insert(idcFunctionDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改
   *
   * @param id
   * @param mtdLabelsVO
   * @return DefaultCommonResult
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(
      @PathVariable("id") Long id, @RequestBody @Validated IdcFunctionDTO mtdLabelsVO) {
    idcFunctionService.update(id, mtdLabelsVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除
   *
   * @param ids id集合，字符串
   * @return DefaultCommonResult
   */
  @DeleteMapping("/{ids}")
  public DefaultCommonResult delete(@PathVariable("ids") String ids) {
    idcFunctionService.delete(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 详情
   *
   * @param id
   * @return DefaultCommonResult<IdcFunctionVO>
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<IdcFunctionVO> detail(@PathVariable("id") Long id) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, idcFunctionService.detail(id));
  }

  /**
   * 下拉列表
   *
   * @param engine 引擎
   * @return DefaultCommonResult<Map<String, List<IdcFunctionVO>>>
   */
  @GetMapping("")
  public DefaultCommonResult<Map<String, List<IdcFunctionVO>>> listByAll(String engine) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, idcFunctionService.getList(engine));
  }

  /**
   * 分页列表
   *
   * @param idcFunctionPageDTO
   * @return DefaultCommonResult<PageResultVO<IdcFunctionVO>>
   */
  @GetMapping("/page")
  public DefaultCommonResult<PageResultVO<IdcFunctionVO>> listByPage(
          @RequestBody IdcFunctionPageDTO idcFunctionPageDTO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, idcFunctionService.listByPage(idcFunctionPageDTO));
  }

  /**
   * 函数类型
   *
   * @return DefaultCommonResult<Map<Object, String>>
   */
  @GetMapping("/type")
  public DefaultCommonResult<Map<Object, String>> type() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, FunctionTypeEnum.EnumToMap());
  }

  /**
   * 统计周期
   * @return DefaultCommonResult<Map<Object, String>>
   */
  @GetMapping("/cycle")
  public DefaultCommonResult<Map<Object, String>> cycle() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, DateCycleEnum.EnumToMap());
  }
}
