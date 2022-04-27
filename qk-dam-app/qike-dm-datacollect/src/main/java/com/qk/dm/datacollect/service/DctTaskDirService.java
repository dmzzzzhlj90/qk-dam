package com.qk.dm.datacollect.service;

import com.qk.dm.datacollect.vo.DctTaskDirTreeVO;
import com.qk.dm.datacollect.vo.DctTaskDirVO;

import java.util.List;

public interface DctTaskDirService {
  void insert(DctTaskDirVO dctTaskDirVO);

  void update(DctTaskDirVO dctTaskDirVO);

  void delete(String id);

  void deleteBulk(String ids);

  List<DctTaskDirTreeVO> searchList();
}
