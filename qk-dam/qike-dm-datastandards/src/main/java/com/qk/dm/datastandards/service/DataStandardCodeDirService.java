package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import com.qk.dm.datastandards.vo.DsdCodeDirVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准码表目录接口
 */
@Service
public interface DataStandardCodeDirService {

  List<DataStandardCodeTreeVO> getTree();

  void addDsdDir(DsdCodeDirVO dsdCodeDirVO);

  void updateDsdDir(DsdCodeDirVO dsdCodeDirVO);

  void deleteDsdDir(Integer delId);

  void deleteDsdDirRoot(Integer delId);

    Boolean deleteJudgeDsdDir(Integer id);
}
