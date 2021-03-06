package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.entity.DasApiDir;
import com.qk.dm.dataservice.vo.DasApiDirTreeVO;
import com.qk.dm.dataservice.vo.DasApiDirVO;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public interface DasApiDirService {

  List<DasApiDirTreeVO> searchList();

  void insert(DasApiDirVO dasApiDirVO);

  void update(DasApiDirVO dasApiDirVO);

  void delete(String id);

  void deleteBulk(String ids);

  void getApiDirId(Set<String> dirIdSet, String dirId);

  List<DasApiDirVO> getDasApiDirByDirName(String title);

  DasApiDir searchApiDirInfoByDirId(String dirId);

}
