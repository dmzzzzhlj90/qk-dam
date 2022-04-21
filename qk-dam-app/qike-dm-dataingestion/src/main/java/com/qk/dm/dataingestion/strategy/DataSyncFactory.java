package com.qk.dm.dataingestion.strategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.model.*;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
/**
 * 离线数据同步工厂
 * @author wangzp
 * @date 2022/04/09 14:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataSyncFactory {

    private final List<DataxJson> dataList;

    private Map<IngestionType,DataxJson> maps;

    public DataSyncFactory(List<DataxJson> dataList) {
        this.dataList = dataList;
    }

    @PostConstruct
    private void init() {
        log.info("数据引入类型初始化开始");
        maps = Optional.ofNullable(dataList).orElse(new ArrayList<>(0)).stream().collect(Collectors.toMap(
                DataxJson::ingestionType, t -> t));
        log.info("数据引入类型初始化完成");
    }


    public DataxJson getIngestionTyp(IngestionType ingestionTyp) {
        return maps.get(ingestionTyp);
    }

    /**
     *  统一json组装模板
     * @param dataMigrationVO 作业数据对象
     * @param readerType 读类型
     * @param writerType 写类型
     * @return String json字符串
     */
    public String transJson(DataMigrationVO dataMigrationVO, IngestionType readerType, IngestionType writerType){
        log.info("数据引入读类型【{}】,数据引入写入类型【{}】",readerType,writerType);
        List<DataxContent> contents = List.of(DataxContent.builder()
                .reader(getIngestionTyp(readerType).getReader(dataMigrationVO))
                .writer(getIngestionTyp(writerType).getWriter(dataMigrationVO)).build());

        DataxJob dataxJob = DataxJob.builder().content(contents)
                .setting(new DataxSetting(dataMigrationVO.getSchedulerConfig())).build();
        String jsonString = parseJson(GsonUtil.toJsonString(Map.of("job", dataxJob)));
        log.info("生成的datax json: {}",jsonString);
        return jsonString;
    }

    /**
     * 格式化json字符串
     * @param jsonString
     * @return
     */
    private String parseJson(String jsonString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(JsonParser.parseString(jsonString));

    }

}
