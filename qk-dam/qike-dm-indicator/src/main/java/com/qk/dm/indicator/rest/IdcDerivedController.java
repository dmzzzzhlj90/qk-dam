package com.qk.dm.indicator.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.indicator.common.sqlbuilder.SqlBuilder;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcDerivedDTO;
import com.qk.dm.indicator.params.dto.IdcDerivedPageDTO;
import com.qk.dm.indicator.params.vo.IdcAtomVO;
import com.qk.dm.indicator.params.vo.IdcDerivedVO;
import com.qk.dm.indicator.service.IdcAtomService;
import com.qk.dm.indicator.service.IdcDerivedService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 指标通用限定相关接口
 *
 * @author wangzp
 * @date 2021/9/5 15:42
 * @since 1.0.0
 */
@RestController
@RequestMapping("/derived")
public class IdcDerivedController {
    private final IdcDerivedService idcDerivedService;
    private final IdcAtomService idcAtomService;

    public IdcDerivedController(IdcDerivedService idcDerivedService,IdcAtomService idcAtomService){
        this.idcDerivedService = idcDerivedService;
        this.idcAtomService = idcAtomService;
    }
    /**
     * 新增
     *
     * @param idcDerivedDTO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated IdcDerivedDTO idcDerivedDTO) {
        idcDerivedService.insert(idcDerivedDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改
     *
     * @param id
     * @param idcDerivedDTO
     * @return DefaultCommonResult
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(
            @PathVariable("id") Long id, @RequestBody @Validated IdcDerivedDTO idcDerivedDTO) {
        idcDerivedService.update(id, idcDerivedDTO);
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
        idcDerivedService.publish(id);
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
        idcDerivedService.offline(id);
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
        idcDerivedService.delete(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 详情
     *
     * @param id
     * @return DefaultCommonResult<IdcAtomVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<IdcDerivedVO> detail(@PathVariable("id") Long id) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, idcDerivedService.detail(id));
    }

    /**
     * 分页列表
     *
     * @param idcDerivedPageDTO
     * @return DefaultCommonResult<PageResultVO<IdcAtomPageVO>>
     */
    @GetMapping("/page")
    public DefaultCommonResult<PageResultVO<IdcDerivedVO>> listByPage(
            @RequestBody IdcDerivedPageDTO idcDerivedPageDTO) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, idcDerivedService.findListPage(idcDerivedPageDTO));
    }

    /**
     * SQL预览
     * @param atomicCode 原子指标编码
     * @param generalLimit 通用限定
     * @return
     */
    @GetMapping("/preview")
    public DefaultCommonResult<String> sqlPreview(@RequestParam("atomicCode") String atomicCode,
                                                  @RequestParam("generalLimit") String generalLimit){
        IdcAtomVO idcAtomVO = idcAtomService.getDetailByCode(atomicCode);
        return DefaultCommonResult.success(
                ResultCodeEnum.OK,
                SqlBuilder.derived(idcAtomVO.getExpression(),idcAtomVO.getDataSheet(),generalLimit));
    }

}
