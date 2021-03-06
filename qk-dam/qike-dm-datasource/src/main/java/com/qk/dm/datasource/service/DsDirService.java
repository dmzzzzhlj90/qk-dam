package com.qk.dm.datasource.service;

import com.qk.dm.datasource.vo.DsDirReturnVO;
import com.qk.dm.datasource.vo.DsDirVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 数据连接目录接口
 *
 * @author zys
 * @date 20210729
 * @since 1.0.0
 */
@Service
public interface DsDirService {

  void addDsDir(DsDirVO dsDirVO);

  void deleteDsDir(String id);

  List<DsDirReturnVO> getDsDir();

  void getDsdId(Set<String> dsDicIdSet, String dicId);

  List<DsDirReturnVO> getDsDirDataSource();

  void updateDsDir(DsDirVO dsDirVO);
}
