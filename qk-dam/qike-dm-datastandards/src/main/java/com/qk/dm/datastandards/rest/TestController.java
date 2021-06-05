package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.vo.TestVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestController {
    final DsdBasicinfoRepository dsdBasicinfoRepository;

    public TestController(DsdBasicinfoRepository dsdBasicinfoRepository) {
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
    }

    @GetMapping("/test")
    public <T> DefaultCommonResult<T> test(@Valid TestVO testVO){
        return new DefaultCommonResult(ResultCodeEnum.OK,dsdBasicinfoRepository.findAll());
    }

}
