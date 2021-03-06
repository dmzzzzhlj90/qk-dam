package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.params.dto.DqcRuleTemplateParamsDTO;
import com.qk.dm.dataquality.params.dto.DqcRuleTemplateReleaseDTO;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.vo.DqcRuleTemplateInfoVO;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVO;
import com.qk.dm.dataquality.vo.RuleTemplateConstantsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据质量_规则模版
 *
 * @author shenpj
 * @date 2021/11/8 7:45 下午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/rule/template")
public class DqcRuleTemplateController {

    private final DqcRuleTemplateService dqcRuleTemplateService;

    @Autowired
    public DqcRuleTemplateController(DqcRuleTemplateService dqcRuleTemplateService) {
        this.dqcRuleTemplateService = dqcRuleTemplateService;
    }

    /**
     * 新增规则模版
     *
     * @param dqcRuleTemplateVo
     * @return DefaultCommonResult
     */
    @PostMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DqcRuleTemplateVO dqcRuleTemplateVo) {
        dqcRuleTemplateService.insert(dqcRuleTemplateVo);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑规则模版
     *
     * @param dqcRuleTemplateVo
     * @return DefaultCommonResult
     */
    @PutMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DqcRuleTemplateVO dqcRuleTemplateVo) {
        dqcRuleTemplateService.update(dqcRuleTemplateVo);
        return DefaultCommonResult.success();
    }

    /**
     * 发布
     *
     * @param dqcRuleTemplateReleaseDto
     * @return DefaultCommonResult
     */
    @PutMapping("/release")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult release(
            @RequestBody @Validated DqcRuleTemplateReleaseDTO dqcRuleTemplateReleaseDto) {
        dqcRuleTemplateService.release(dqcRuleTemplateReleaseDto);
        return DefaultCommonResult.success();
    }

    /**
     * 删除单个规则模版
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{id}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteOne(@PathVariable("id") Long id) {
        dqcRuleTemplateService.deleteOne(id);
        return DefaultCommonResult.success();
    }

    /**
     * 批量删除规则模版
     *
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/bulk")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@NotNull @RequestParam("ids") String ids) {
        dqcRuleTemplateService.deleteBulk(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 规则模版详情
     *
     * @param id
     * @return DefaultCommonResult<DqcRuleTemplateInfoVO>
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<DqcRuleTemplateInfoVO> detail(@PathVariable("id") Long id) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleTemplateService.detail(id));
    }

    /**
     * 规则模版下拉列表
     *
     * @param dqcRuleTemplateParamsDto
     * @return DefaultCommonResult<List < DqcRuleTemplateInfoVO>>
     */
    @GetMapping("/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<List<DqcRuleTemplateInfoVO>> search(
            DqcRuleTemplateParamsDTO dqcRuleTemplateParamsDto) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dqcRuleTemplateService.search(dqcRuleTemplateParamsDto));
    }

    /**
     * 规则模版分页列表
     *
     * @param dqcRuleTemplateParamsDto
     * @return DefaultCommonResult<PageResultVO < DqcRuleTemplateInfoVO>>
     */
    @PostMapping("/page/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DqcRuleTemplateInfoVO>> searchPageList(
            @RequestBody DqcRuleTemplateParamsDTO dqcRuleTemplateParamsDto) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dqcRuleTemplateService.searchPageList(dqcRuleTemplateParamsDto));
    }

    /**
     * 获取规则模版常量信息
     *
     * @return DefaultCommonResult<RuleTemplateConstantsVO>
     */
    @GetMapping("/constants")
    public DefaultCommonResult<RuleTemplateConstantsVO> getRuLeTemplateConstants() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleTemplateService.getRuLeTemplateConstants());
    }

    /**
     * 根据模板ID获取结果模板定义
     *
     * @return DefaultCommonResult<RuleTemplateConstantsVO>
     */
    @GetMapping("/result/info")
    public DefaultCommonResult getTempResultByTempId(@RequestParam("tempId") Long tempId) {
      return DefaultCommonResult.success(ResultCodeEnum.OK, dqcRuleTemplateService.getTempResultByTempId(tempId),"查询结果模板定义");
    }

}
