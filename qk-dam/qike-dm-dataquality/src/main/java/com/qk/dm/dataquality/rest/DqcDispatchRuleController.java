package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.service.DqcDispatchRuleService;
import com.qk.dm.dataquality.vo.DqcDispatchRuleListVo;
import com.qk.dm.dataquality.vo.DqcDispatchRuleVo;
import com.qk.dm.dataquality.vo.PageResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/9 10:52 上午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dispatch/rule")
public class DqcDispatchRuleController {

    private final DqcDispatchRuleService dqcDispatchRuleService;


    public DqcDispatchRuleController(DqcDispatchRuleService dqcDispatchRuleService) {
        this.dqcDispatchRuleService = dqcDispatchRuleService;
    }

    @GetMapping("/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<List<DqcDispatchRuleListVo>> searchList() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcDispatchRuleService.searchList());
    }

    @GetMapping("/page/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DqcDispatchRuleListVo>> searchPageList(
            Pagination pagination) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dqcDispatchRuleService.searchPageList(pagination));
    }

    @PostMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DqcDispatchRuleVo dqcDispatchRuleVo) {
        dqcDispatchRuleService.insert(dqcDispatchRuleVo);
        return DefaultCommonResult.success();
    }

    @PutMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DqcDispatchRuleVo dqcDispatchRuleVo) {
        dqcDispatchRuleService.update(dqcDispatchRuleVo);
        return DefaultCommonResult.success();
    }

    @DeleteMapping("/{id}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult delete(@PathVariable("id") Integer id) {
        dqcDispatchRuleService.delete(id);
        return DefaultCommonResult.success();
    }

    @DeleteMapping("/root/{id}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("id") Integer id) {
        dqcDispatchRuleService.deleteBulk(id);
        return DefaultCommonResult.success();
    }
}
