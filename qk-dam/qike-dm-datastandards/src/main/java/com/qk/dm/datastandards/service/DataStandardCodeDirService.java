package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准__码表目录接口
 */
@Service
public interface DataStandardCodeDirService {

    List<DataStandardCodeTreeVO> getTree();

    void addDsdDir(DsdCodeDir dsdCodeDir);

    void updateDsdDir(DsdCodeDir dsdCodeDir);

    void deleteDsdDir(Integer delId);

    void deleteDsdDirRoot(Integer delId);

}
