package com.qk.dm.datastandards.mapstruct.mapper;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:46+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class CarMapperImpl implements CarMapper {

    @Override
    public CarDto carToCarDto(Car car) {
        if ( car == null ) {
            return null;
        }

        int seatCount = 0;
        String make = null;

        seatCount = car.getNumberOfSeats();
        make = car.getMake();

        String type = null;

        CarDto carDto = new CarDto( make, seatCount, type );

        return carDto;
    }
}
