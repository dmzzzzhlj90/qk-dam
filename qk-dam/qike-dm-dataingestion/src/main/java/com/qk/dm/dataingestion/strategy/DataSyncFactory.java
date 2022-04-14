package com.qk.dm.dataingestion.strategy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataingestion.model.*;
import com.qk.dm.dataingestion.vo.DataMigrationVO;
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
@Component
public class DataSyncFactory {

    private final List<DataxJson> dataList;

    private Map<IngestionType,DataxJson> maps;

    public DataSyncFactory(List<DataxJson> dataList) {
        this.dataList = dataList;
    }

    @PostConstruct
    private void init() {
        maps = Optional.ofNullable(dataList).orElse(new ArrayList<>(0)).stream().collect(Collectors.toMap(
                DataxJson::ingestionType, t -> t));
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

        List<DataxContent> contents = List.of(DataxContent.builder()
                .reader(getIngestionTyp(readerType).getReader(dataMigrationVO))
                .writer(getIngestionTyp(writerType).getWriter(dataMigrationVO)).build());

        DataxJob dataxJob = DataxJob.builder().content(contents)
                .setting(new DataxSetting(dataMigrationVO.getSchedulerConfig())).build();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("job",dataxJob);
        return parseJson(GsonUtil.toJsonString(map));
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
