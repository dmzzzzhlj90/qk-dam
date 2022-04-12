package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataingestion.service.DisRuleClassService;
import com.qk.dm.dataingestion.vo.DisRuleClassVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 规则分类
 */
@RestController
@RequestMapping("/rule/class")
public class DisRuleClassController {

    public final DisRuleClassService DisRuleClassService;

    public DisRuleClassController(DisRuleClassService disRuleClassService) {
        DisRuleClassService = disRuleClassService;
    }


    /**
     * 获取规则列表
     * @return DefaultCommonResult<List<DisRuleClassVO>>
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<DisRuleClassVO>> list(){
        return  DefaultCommonResult.success(ResultCodeEnum.OK,DisRuleClassService.list());
    }
}
