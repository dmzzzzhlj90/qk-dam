package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.vo.DataStandardTreeRespVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjq
 * @date 2021060
 * 数据标准__目录接口
 * @since 1.0.0
 */
@Service
public interface DataStandardDirService {

    List<DataStandardTreeRespVO> getTree();

    void addDsdDir(DsdDir dsdDir);

    void updateDsdDir(DsdDir dsdDir);

    void deleteDsdDir(Integer delId);

    void deleteDsdDirRoot(Integer delId);

}
