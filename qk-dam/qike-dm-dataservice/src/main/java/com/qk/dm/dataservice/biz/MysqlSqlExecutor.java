package com.qk.dm.dataservice.biz;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dm.dataservice.utils.SqlExecuteUtils;
import com.qk.dm.dataservice.vo.DasApiCreateOrderParasVO;
import com.qk.dm.dataservice.vo.DasApiCreateRequestParasVO;
import com.qk.dm.dataservice.vo.DasApiCreateResponseParasVO;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MYSQL 执行器
 *
 * @author wjq
 * @date 2022/03/07
 * @since 1.0.0
 */
public class MysqlSqlExecutor {
    private final Db use;
    private final String host;
    private final String db;
    private String sql;
    private Map<String, String> reqParams;
    private List<DasApiCreateResponseParasVO> responseParas;

    public MysqlSqlExecutor(ConnectBasicInfo connectBasicInfo,
                            String dataBaseName,
                            Map<String, String> reqParams,
                            List<DasApiCreateResponseParasVO> responseParas) {
        this.use =
                Db.use(
                        new SimpleDataSource(
                                "jdbc:mysql://"
                                        + connectBasicInfo.getServer() +
                                        ":"
                                        + connectBasicInfo.getPort()
                                        + "/"
                                        + dataBaseName,
                                connectBasicInfo.getUserName(),
                                connectBasicInfo.getPassword())
                );
        this.host = connectBasicInfo.getServer();
        this.db = dataBaseName;
        this.reqParams = reqParams;
        this.responseParas = responseParas;
    }

    /**
     * 执行sql
     *
     * @param tableName
     * @param sqlPara   取数脚本
     * @param orderByStr
     * @return
     */
    public MysqlSqlExecutor mysqlExecuteSQL(String tableName,
                                            String sqlPara,
                                            Map<String, List<DasApiCreateRequestParasVO>> mappingParams,
                                            String orderByStr) {
        if (ObjectUtils.isEmpty(sqlPara)) {
            //生成查询sql
            this.sql = SqlExecuteUtils.mysqlExecuteSQL(tableName, reqParams, responseParas, mappingParams,orderByStr);
        } else {
            this.sql = SqlExecuteUtils.mysqlSqlPara(sqlPara, reqParams,orderByStr);
        }
        return this;
    }


    /**
     * 执行sql获取查询结果集
     *
     * @return
     */
    public List<Map<String, Object>> searchData() {
        List<Map<String, Object>> result = null;
        use.setCaseInsensitive(false);
        try {
            List<Entity> searchDataList = use.query(sql);
            result =
                    searchDataList.stream()
                            .map(this::buildSelectData)
                            .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("mysql查询数据失败!!!");
        }
        return result;
    }

//    /**
//     * 根据响应参数构建查询数据信息
//     *
//     * @param entity
//     * @param responseParas
//     * @return
//     */
//    private Map<String, Object> buildResponseData(Entity entity, List<DasApiCreateResponseParasVO> responseParas) {
//        HashMap<String, Object> resultMap = Maps.newHashMap();
//
//        for (DasApiCreateResponseParasVO responsePara : responseParas) {
//            String mappingName = responsePara.getMappingName();
//            // key设置为响应参数信息,value通过字段获取到查询信息
//            resultMap.put(mappingName, entity.get(mappingName));
//        }
//        return resultMap;
//    }

    /**
     * 执行sql片段获取查询结果集,不需要处理返回值信息
     *
     * @return
     */
    public List<Map<String, Object>> searchDataSqlPara() {
        List<Map<String, Object>> result = null;
        use.setCaseInsensitive(false);
        try {
            List<Entity> searchDataList = use.query(sql);
            result =
                    searchDataList.stream()
                            .map(this::buildSelectData)
                            .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("mysql查询数据失败!!!");
        }
        return result;
    }

    /**
     * 根据查询字段构建查询数据信息
     *
     * @param entity
     * @return
     */
    private Map<String, Object> buildSelectData(Entity entity) {
        HashMap<String, Object> resultMap = Maps.newHashMap();

        for (String col : entity.getFieldNames()) {
            // key设置为响应参数信息,value通过字段获取到查询信息
            resultMap.put(col, entity.get(col));
        }
        return resultMap;
    }


}
