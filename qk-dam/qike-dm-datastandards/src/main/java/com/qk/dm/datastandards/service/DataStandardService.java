package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.vo.DataStandardTreeResp;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjq
 * @date 20210603
 * 数据标准接口
 * @since 1.0.0
 */
@Service
public interface DataStandardService {

    List<DataStandardTreeResp> getTree();

    void addDsdDir(DsdDir dsdDir);

    void updateDsdDir(DsdDir dsdDir);

    void deleteDsdDir(Integer id);

}
