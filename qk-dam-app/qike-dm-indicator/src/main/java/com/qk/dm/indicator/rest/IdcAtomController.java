package com.qk.dm.indicator.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.indicator.common.sqlbuilder.SqlBuilder;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcAtomDTO;
import com.qk.dm.indicator.params.dto.IdcAtomPageDTO;
import com.qk.dm.indicator.params.vo.IdcAtomPageVO;
import com.qk.dm.indicator.params.vo.IdcAtomVO;
import com.qk.dm.indicator.service.IdcAtomService;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 原子指标功能
 *
 * @author shenpj
 * @date 2021/9/2 8:38 下午
 * @since 1.0.0
 */
@RestController
@RequestMapping("/atom")
public class IdcAtomController {
  private final IdcAtomService idcAtomService;

  public IdcAtomController(IdcAtomService idcAtomService) {
    this.idcAtomService = idcAtomService;
  }

  /**
   * 新增
   *
   * @param idcAtomDTO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody @Validated IdcAtomDTO idcAtomDTO) {
    idcAtomService.insert(idcAtomDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 修改
   *
   * @param id
   * @param idcAtomDTO
   * @return DefaultCommonResult
   */
  @PutMapping("/{id}")
  public DefaultCommonResult update(
      @PathVariable("id") Long id, @RequestBody @Validated IdcAtomDTO idcAtomDTO) {
    idcAtomService.update(id, idcAtomDTO);
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
    idcAtomService.publish(id);
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
    idcAtomService.offline(id);
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
    idcAtomService.delete(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 详情
   *
   * @param id
   * @return DefaultCommonResult<IdcAtomVO>
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<IdcAtomVO> detail(@PathVariable("id") Long id) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, idcAtomService.detail(id));
  }

  /**
   * 下拉列表
   *
   * @return DefaultCommonResult<List<IdcAtomVO>>
   */
  @GetMapping("")
  public DefaultCommonResult<List<IdcAtomVO>> listByAll() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, idcAtomService.getList());
  }

  /**
   * 分页列表
   *
   * @param idcAtomPageDTO
   * @return DefaultCommonResult<PageResultVO<IdcAtomPageVO>>
   */
  @GetMapping("/page")
  public DefaultCommonResult<PageResultVO<IdcAtomPageVO>> listByPage(
      @RequestBody IdcAtomPageDTO idcAtomPageDTO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, idcAtomService.listByPage(idcAtomPageDTO));
  }

  /**
   * SQL预览
   *
   * @param dataSheet 数据库表
   * @param expression 表达式
   * @return String
   */
  @GetMapping("/preview")
  public DefaultCommonResult<String> sqlPreview(
      @RequestParam("dataSheet") String dataSheet, @RequestParam("expression") String expression) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, SqlBuilder.atomicSql(expression, dataSheet));
  }
}
