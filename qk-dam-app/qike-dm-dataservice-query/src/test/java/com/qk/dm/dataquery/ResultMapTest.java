package com.qk.dm.dataquery;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qk.dm.dataquery.model.ResultMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;

import java.util.List;

@SpringBootTest(classes = SpringBootTestContextBootstrapper.class)
@Slf4j
@Disabled
class ResultMapTest {
    @Test
    void resultBuildTest() throws JsonProcessingException {
        ResultMap resultMap = ResultMap.builder()
                .autoMapping(true)
                .id("rt")
                .type("HashMap")
                .result(
                        List.of(
                                new ResultMap.Result("id", "id")))
                .association(
                        List.of(new ResultMap.Association("ac","HashMap",List.of(
                                new ResultMap.Result("id", "id"),
                                new ResultMap.Result("www", "id"))
                        ))
                )
                .collection(List.of(new ResultMap.Collection(
                        "ac","HashMap",
                        List.of(
                                new ResultMap.Result("id", "id"),
                                new ResultMap.Result("www", "id"))
                        )
                ))
                .build();

        ObjectMapper om = new XmlMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String s = om.writeValueAsString(resultMap);
        log.info("test result map build=====> {}",s);

    }
}
