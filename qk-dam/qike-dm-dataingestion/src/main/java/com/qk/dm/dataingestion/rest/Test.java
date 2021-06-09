package com.qk.dm.dataingestion.rest;

import com.qk.dam.sqlloader.repo.PiciTaskAgg;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author wjq
 * @date 2021/6/9 15:33
 * @since 1.0.0
 */
public class Test {
    public static void main(String[] args) {

        List<PiciTaskVO> piciTasks = PiciTaskAgg.longgovTaskrizhi(1);
        System.out.println("========+++++++"+piciTasks);
    }
}
