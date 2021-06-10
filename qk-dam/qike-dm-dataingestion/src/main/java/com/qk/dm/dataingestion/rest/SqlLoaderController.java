package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.sqlloader.SqlLoaderMain;
import com.qk.dam.sqlloader.repo.QkUpdated1Agg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sqlloader")
public class SqlLoaderController {
    @GetMapping("/exec/{date}")
    public DefaultCommonResult syncRiZhiFilesData(@PathVariable String date) {
        SqlLoaderMain.executeTask(date);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    @GetMapping("/exec/writeDest/{date}")
    public DefaultCommonResult writeDestTask(@PathVariable String date) {
        SqlLoaderMain.writeDestTask(date);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }
    @GetMapping("/exec/updateToUpdate1")
    public DefaultCommonResult syncRiZhiFilesData() {
        QkUpdated1Agg.qkUpdatedBatchSql();
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}
