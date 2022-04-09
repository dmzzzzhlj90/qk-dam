package com.qk.dm.indicator.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcTimeLimitDTO;
import com.qk.dm.indicator.params.dto.IdcTimeLimitPageDTO;
import com.qk.dm.indicator.params.vo.IdcTimeLimitVO;
import com.qk.dm.indicator.service.IdcTimeLimitService;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 指标通用限定相关接口
 *
 * @author wangzp
 * @date 2021/9/1 15:51
 * @since 1.0.0
 */
@RestController
@RequestMapping("/time/limit")
public class IdcTimeLimitController {

  private final IdcTimeLimitService idcTimeLimitService;

  public IdcTimeLimitController(IdcTimeLimitService idcTimeLimitService) {
    this.idcTimeLimitService = idcTimeLimitService;
  }

  /**
   * 添加通用时间限定
   *
   * @param idcTimeLimitDTO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult add(@RequestBody @Validated IdcTimeLimitDTO idcTimeLimitDTO) {
    idcTimeLimitService.insert(idcTimeLimitDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改通用时间限定
   *
   * @param id
   * @param idcTimeLimitDTO
   * @return
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(
      @PathVariable("id") Long id, @RequestBody @Validated IdcTimeLimitDTO idcTimeLimitDTO) {
    idcTimeLimitService.update(id, idcTimeLimitDTO);
    return DefaultCommonResult.success();
  }

  @DeleteMapping("/{ids}")
  public DefaultCommonResult delete(@PathVariable("ids") String ids) {
    idcTimeLimitService.delete(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 获取通用限定时间详情
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<IdcTimeLimitVO> detail(@PathVariable("id") Long id) {
    IdcTimeLimitVO idcTimeLimitVO = idcTimeLimitService.detail(id);
    return DefaultCommonResult.success(ResultCodeEnum.OK, idcTimeLimitVO);
  }

  /**
   * 获取全部可用通用时间限定
   *
   * @return DefaultCommonResult<List < IdcTimeLimitVO>>
   */
  @GetMapping("/find/all")
  public DefaultCommonResult<List<IdcTimeLimitVO>> findAll() {
    List<IdcTimeLimitVO> idcTimeLimitVOList = idcTimeLimitService.findAll();
    return DefaultCommonResult.success(ResultCodeEnum.OK, idcTimeLimitVOList);
  }

  /**
   * @param idcTimeLimitPageDTO
   * @return DefaultCommonResult<PageResultVO < IdcTimeLimitVO>>
   */
  @GetMapping("/page")
  public DefaultCommonResult<PageResultVO<IdcTimeLimitVO>> pageList(
      @RequestBody IdcTimeLimitPageDTO idcTimeLimitPageDTO) {
    PageResultVO<IdcTimeLimitVO> list = idcTimeLimitService.findListPage(idcTimeLimitPageDTO);
    return DefaultCommonResult.success(ResultCodeEnum.OK, list);
  }
}
