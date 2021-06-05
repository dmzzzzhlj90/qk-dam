package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准目录接口
 */
@Service
public interface DataStandardDirService {

    List<DataStandardTreeVO> getTree();

    void addDsdDir(DsdDir dsdDir);

    void updateDsdDir(DsdDir dsdDir);

    void deleteDsdDir(Integer delId);

    void deleteDsdDirRoot(Integer delId);

}
