package com.qk.dm.indicator.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcCompositeDTO;
import com.qk.dm.indicator.params.dto.IdcCompositePageDTO;
import com.qk.dm.indicator.params.vo.IdcCompositePageVO;
import com.qk.dm.indicator.params.vo.IdcCompositeVO;
import com.qk.dm.indicator.service.IdcCompositeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 复合指标功能
 *
 * @author shenpj
 * @date 2021/9/2 8:38 下午
 * @since 1.0.0
 */
@RestController
@RequestMapping("/composite")
public class IdcCompositeController {

  private final IdcCompositeService idcCompositeService;

  public IdcCompositeController(IdcCompositeService idcCompositeService) {
    this.idcCompositeService = idcCompositeService;
  }


  /**
   * 新增
   *
   * @param idcCompositeDTO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody @Validated IdcCompositeDTO idcCompositeDTO) {
    idcCompositeService.insert(idcCompositeDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改
   *
   * @param id
   * @param idcCompositeDTO
   * @return DefaultCommonResult
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(
      @PathVariable("id") Long id, @RequestBody @Validated IdcCompositeDTO idcCompositeDTO) {
    idcCompositeService.update(id, idcCompositeDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 发布
   *
   * @param id
   * @return DefaultCommonResult
   */
  @PutMapping("/publish/{id}")
  public DefaultCommonResult publish(@PathVariable("id") Long id) {
    idcCompositeService.publish(id);
    return DefaultCommonResult.success();
  }

  /**
   * 下线
   *
   * @param id
   * @return DefaultCommonResult
   */
  @PutMapping("/offline/{id}")
  public DefaultCommonResult offline(@PathVariable("id") Long id) {
    idcCompositeService.offline(id);
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
    idcCompositeService.delete(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 详情
   *
   * @param id
   * @return DefaultCommonResult<IdcCompositeVO>
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<IdcCompositeVO> detail(@PathVariable("id") Long id) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, idcCompositeService.detail(id));
  }

  /**
   * 下拉列表
   *
   * @return DefaultCommonResult<List<IdcCompositeVO>>
   */
  @GetMapping("")
  public DefaultCommonResult<List<IdcCompositeVO>> listByAll() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, idcCompositeService.getList());
  }

  /**
   * 分页列表
   *
   * @param idcCompositePageDTO
   * @return DefaultCommonResult<PageResultVO<IdcCompositePageVO>>
   */
  @GetMapping("/page")
  public DefaultCommonResult<PageResultVO<IdcCompositePageVO>> listByPage(
          @RequestBody IdcCompositePageDTO idcCompositePageDTO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, idcCompositeService.listByPage(idcCompositePageDTO));
  }
}
