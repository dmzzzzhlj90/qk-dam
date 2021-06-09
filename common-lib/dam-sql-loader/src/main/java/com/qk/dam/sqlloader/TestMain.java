package com.qk.dam.sqlloader;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.util.Date;
import java.util.List;

import static com.qk.dam.sqlloader.DmSqlLoader.getPiciTask;

public class TestMain {
    public static void main(String[] args) {
        List<PiciTaskVO> piciTask = getPiciTask();

        for (PiciTaskVO piciTaskVO : piciTask) {
            PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(piciTaskVO.getPici(),piciTaskVO.getTableName(),0,new Date()));
        }

    }

}