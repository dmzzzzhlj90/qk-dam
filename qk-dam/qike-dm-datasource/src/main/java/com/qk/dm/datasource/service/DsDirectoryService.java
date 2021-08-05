package com.qk.dm.datasource.service;

import com.qk.dm.datasource.vo.DsDirectoryVO;
import com.qk.dm.datasource.vo.Pagination;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zys
 * @date 20210729
 * @since 1.0.0 数据源管理应用系统录入接口
 */
@Service
public interface DsDirectoryService {
    List<DsDirectoryVO> getSysDirectory(Pagination pagination);

    void addSysDirectory(DsDirectoryVO dsDirectoryVO);

    void deleteDsDirectory(Integer id);
}
