package com.qk.dam.sqlloader;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.repo.PiciTaskAgg;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.qk.dam.sqlloader.DmSqlLoader.getPiciTask;

public class TestMain {
    public static void main(String[] args) {
        List<PiciTaskVO> piciTask = getPiciTask();
        System.out.println(piciTask);
    }

}