package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import com.qk.dm.datastandards.vo.DsdDirVO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准目录接口
 */
@Service
public interface DataStandardDirService {

  List<DataStandardTreeVO> getTree();

  void addDsdDir(DsdDirVO dsdDirVO);

  void updateDsdDir(DsdDirVO dsdDirVO);

  void deleteDsdDir(Integer delId);

  void deleteDsdDirRoot(Integer delId);

  Boolean deleteJudgeDsdDir(Integer id);

}
