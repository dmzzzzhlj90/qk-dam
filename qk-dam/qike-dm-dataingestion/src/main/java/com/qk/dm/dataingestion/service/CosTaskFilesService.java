package com.qk.dm.dataingestion.service;

import com.qk.dm.dataingestion.vo.CosTaskFileInfoVO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 同步获取COS客户端文件信息
 *
 * @author wjq
 * @date 2021/06/28
 * @since 1.0.0
 */
@Service
public interface CosTaskFilesService {

  List<CosTaskFileInfoVO> getCosTaskFilesInfo(String dataDay);
}
