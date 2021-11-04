package com.qk.dm.datasource.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.datasource.vo.DsDirectoryVO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author zys
 * @date 20210729
 * @since 1.0.0 数据源管理应用系统录入接口
 */
@Service
public interface DsDirectoryService {
  PageResultVO<DsDirectoryVO> getSysDirectory(Pagination pagination);

  void addSysDirectory(DsDirectoryVO dsDirectoryVO);

  void deleteDsDirectory(Integer id);

  void updateDsDirectory(DsDirectoryVO dsDirectoryVO);

  List<String> getSysName();
}
