//package com.qk.dm.dataquality.config;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.qk.dam.groovy.model.RuleFunctionInfo;
//import com.qk.dm.dataquality.groovy.pojo.RuleFunctionModelInfo;
//import com.qk.dm.dataquality.utils.FileUtil;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Map;
//
///**
// * 加载Groovy函数配置信息 配置类
// *
// * @author wjq
// * @date 2022/1/10
// * @since 1.0.0
// */
//@Configuration
//public class RuleFunctionConfig {
//
//    @Bean
//    public RuleFunctionModelInfo buildRuleFunctionModel() {
//        String result = FileUtil.readFileContent("facts/RuleFunctionModel.json");
//
//        Map<String, RuleFunctionInfo> ruleFunctionInfoMap = new Gson().fromJson(result, new TypeToken<Map<String, RuleFunctionInfo>>() {
//        }.getType());
//
//        return RuleFunctionModelInfo.builder().ruleFunctionInfoMap(ruleFunctionInfoMap).build();
//    }
//}
