package com.qk.dm.indicator.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.indicator.service.IdcFunctionService;
import com.qk.dm.indicator.params.vo.IdcFunctionVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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

    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated IdcFunctionVO idcFunctionVO) {
//        idcFunctionService.insert(idcFunctionVO);
        return DefaultCommonResult.success();
    }
}
