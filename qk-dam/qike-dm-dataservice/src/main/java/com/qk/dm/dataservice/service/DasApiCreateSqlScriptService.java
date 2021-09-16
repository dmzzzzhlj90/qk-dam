package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptVO;
import org.springframework.stereotype.Service;

/**
 * 数据服务_新建API_脚本方式
 *
 * @author wjq
 * @date 20210830
 * @since 1.0.0
 */
@Service
public interface DasApiCreateSqlScriptService {

  DasApiCreateSqlScriptVO getDasApiCreateSqlScriptInfoByApiId(String apiId);

  void addDasApiCreateSqlScript(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO);

  void updateDasApiCreateSqlScript(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO);
}
