package com.qk.dm.datastandards.mapstruct.mapper;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-12T02:36:42+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.5 (Oracle Corporation)"
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
