package com.qk.dm.dataquality.enums;


public enum DimensionEnum {
    INTEGRITY(1,"完整性"),
    UNIQUENESS(2,"唯一性"),
    TIMEBASED(3,"及时性"),
    VALIDITY(4,"有效性"),
    ACCURACY(5,"准确性"),
    UNIFORMITY(6,"一致性");


    private Integer code;
    private String name;

    DimensionEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }


    public static String fromValue(Integer code) {
        for (DimensionEnum b : DimensionEnum.values()) {
            if (b.code.equals(code)) {
                return b.getName();
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
