package com.qk.dm.datastandards.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datastandards.vo.*;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210726
 * @since 1.0.0 数据标准码表管理接口
 */
@Service
public interface DataStandardCodeInfoService {

  /** 码表基本信息_表 */
  PageResultVO<DsdCodeInfoVO> getDsdCodeInfo(DsdCodeInfoParamsVO dsdCodeInfoParamsVO);

  void addDsdCodeInfo(DsdCodeInfoVO dsdCodeInfoVO);

  DsdCodeInfoVO getDsdCodeInfoById(long id);

  void modifyDsdCodeInfo(DsdCodeInfoVO dsdCodeInfoVO);

  void deleteDsdCodeInfo(long id);

  void deleteBulkDsdCodeInfo(String ids);

  List<Map<String, String>> getDataTypes();

  /** 码表扩展信息_码表数值 */
  Map<String, Object> getDsdCodeInfoExt(DsdCodeInfoExtParamsVO dsdCodeInfoExtParamsVO);

  void addDsdCodeInfoExt(DsdCodeInfoExtVO dsdCodeInfoExtVO);

  DsdCodeInfoExtVO getBasicDsdCodeInfoExtById(long id);

  void modifyDsdCodeInfoExt(DsdCodeInfoExtVO dsdCodeInfoExtVO);

  void deleteDsdCodeInfoExt(long id);

  void deleteBulkDsdCodeInfoExt(String ids);

  /** 码表信息逆向数据库操作 */
  void dsdCodeInfoReverseDB(DsdCodeInfoReverseDBVO dsdCodeInfoReverseDBVO);
}
