package com.qk.dm.dataingestion.service;

import com.qk.dm.dataingestion.vo.CosTaskFileInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作数据引入日志表
 *
 * @author wjq
 * @date 2021/07/06
 * @since 1.0.0
 */
@Service
public interface PiciTaskLogService {

  Integer modifyIsHiveByTableNameAndPici(String tableName, String pici);

}
