package com.qk.dm.datastandards.rest;

import com.qk.commons.exception.BizException;
import com.qk.commons.http.result.DefaultCommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 测试解耦解析类
 * @author zhudaoming
 * @date 2020/06/01
 */
@RestController
public class TestController {

    /**
     * 测试解耦test
     * @return  DefaultCommonResult<Map<String,Object>>
     */
    @GetMapping("test")
    public DefaultCommonResult<Map<String,Object>> test() throws Exception {
        Map<String,Object> ssss = Map.of("Ssss", "22222");
        throw new BizException("失败的测试");
    }
}
