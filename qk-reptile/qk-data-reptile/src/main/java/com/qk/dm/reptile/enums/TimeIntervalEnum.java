package com.qk.dm.reptile.enums;

import com.qk.dm.reptile.params.vo.TimeIntervalVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * '定时时间间隔
 */
public enum TimeIntervalEnum {

    NO_SET("0","默认时间"),

    TWENTY("twenty","二十分钟"),

    THIRTY("thirty","三十分钟"),

    SIXTY("sixty","六十分钟"),

    TWO_HOUR("twoHour","两小时"),

    THREE_HOUR("threeHour","三小时"),

    SIX_HOUR("sixHou","六小时"),

    TWELVE_HOUR("twelveHour","十二小时"),

    ONE_DAY("oneDay","二十四小时");


    public static List<TimeIntervalVO> enumToList(){
        List<TimeIntervalVO> timeIntervalList = new ArrayList<>();
        Stream.of(values()).forEach(e->{
            timeIntervalList.add(TimeIntervalVO.builder().name(e.getName()).desc(e.getDesc()).build());
        });
        return timeIntervalList;
    }

    public static String of(String name) {
        return Stream.of(values()).filter(e -> e.getName().equals(name)).findAny().orElse(NO_SET).getDesc();
    }

    private final String name;
    private final String desc;

    TimeIntervalEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
