package com.qk.dm.datastandards.mapstruct.mapper;

public class TestC {
    public static void main(String[] args) {
        CarDto aa = CarMapper.INSTANCE.carToCarDto(new Car("aa", 1));
        System.out.println(aa);
    }
}
