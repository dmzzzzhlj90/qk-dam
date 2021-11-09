package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.service.DqcDispatchTemplateService;
import com.qk.dm.dataquality.vo.DqcDispatchTemplateListVo;
import com.qk.dm.dataquality.vo.DqcDispatchTemplateVo;
import com.qk.dm.dataquality.vo.PageResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/9 10:32 上午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dispatch/template")
public class DqcDispatchTemplateController {

    private final DqcDispatchTemplateService dqcDispatchTemplateService;


    public DqcDispatchTemplateController(DqcDispatchTemplateService dqcDispatchTemplateService) {
        this.dqcDispatchTemplateService = dqcDispatchTemplateService;
    }

    @GetMapping("/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<List<DqcDispatchTemplateListVo>> searchList() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcDispatchTemplateService.searchList());
    }

    @GetMapping("/page/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DqcDispatchTemplateListVo>> searchPageList(
            Pagination pagination) {
        return DefaultCommonResult.success(
                ResultCodeEnum.OK, dqcDispatchTemplateService.searchPageList(pagination));
    }

    @PostMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DqcDispatchTemplateVo dqcDispatchTemplateVo) {
        dqcDispatchTemplateService.insert(dqcDispatchTemplateVo);
        return DefaultCommonResult.success();
    }

    @PutMapping("")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DqcDispatchTemplateVo dqcDispatchTemplateVo) {
        dqcDispatchTemplateService.update(dqcDispatchTemplateVo);
        return DefaultCommonResult.success();
    }

    @DeleteMapping("/{id}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult delete(@PathVariable("id") Integer id) {
        dqcDispatchTemplateService.delete(id);
        return DefaultCommonResult.success();
    }

    @DeleteMapping("/root/{id}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("id") Integer id) {
        dqcDispatchTemplateService.deleteBulk(id);
        return DefaultCommonResult.success();
    }
}
