package com.qk.dm.dataingestion.model;

import com.qk.dm.dataingestion.vo.DisSchedulerConfigVO;
import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.units.qual.Speed;

import java.util.Objects;

@Data
public class DataxSetting {

    private Speed speed;
    private ErrorLimit errorLimit;

    public DataxSetting(DisSchedulerConfigVO schedulerConfig) {
        if(Objects.nonNull(schedulerConfig.getCurrChannel())) {
            this.speed = Speed.builder().channel(schedulerConfig.getCurrChannel()).build();
        }else {
            this.speed = Speed.builder().channel(2).build();
        }
        this.errorLimit =  ErrorLimit.builder()
                .percentage(schedulerConfig.getErrorPercent())
                .record(schedulerConfig.getErrorRecord()).build();
    }

    /**
     * 速率
     */
    @Data
    @Builder
   public static class Speed{

        private Integer channel;
    }

    /**
     * 错误限制
     */
    @Data
    @Builder
    public static class ErrorLimit{
        private Integer record;
        private String percentage;
    }
}
