package com.qk.dm.dataservice.service.imp;

import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiCreateSqlScript;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateSqlScriptMapper;
import com.qk.dm.dataservice.service.DasDataQueryInfoService;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提供数据查询服务配置信息
 *
 * @author zhudaoming
 * @since 1.5
 * @date 20220420
 */
@Service
public class DasDataQueryInfoServiceImpl implements DasDataQueryInfoService {
  public static final String CREATE_API_MYBATIS_SQL_SCRIPT_TYPE =
      "CREATE-API-MYBATIS-SQL-SCRIPT-TYPE";
  private final JPAQueryFactory jpaQueryFactory;

  public DasDataQueryInfoServiceImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public List<DataQueryInfoVO> dataQueryInfo() {
    BooleanExpression whereCondition =
        QDasApiCreateSqlScript.dasApiCreateSqlScript
            .accessMethod
            .eq(CREATE_API_MYBATIS_SQL_SCRIPT_TYPE)
            .and(QDasApiCreateSqlScript.dasApiCreateSqlScript.delFlag.eq(0))
            .and(QDasApiBasicInfo.dasApiBasicInfo.delFlag.eq(0));
    return dataQueryInfo(whereCondition);
  }

  @Override
  public List<DataQueryInfoVO> dataQueryInfo(Long id) {
    BooleanExpression whereCondition =
        QDasApiCreateSqlScript.dasApiCreateSqlScript
            .id
            .eq(id)
            .and(
                QDasApiCreateSqlScript.dasApiCreateSqlScript.accessMethod.eq(
                    CREATE_API_MYBATIS_SQL_SCRIPT_TYPE))
            .and(QDasApiCreateSqlScript.dasApiCreateSqlScript.delFlag.eq(0))
            .and(QDasApiBasicInfo.dasApiBasicInfo.delFlag.eq(0));
    return dataQueryInfo(whereCondition);
  }

  @Override
  public List<DataQueryInfoVO> dataQueryInfoLast(Long time) {
    BooleanExpression whereCondition =
        QDasApiCreateSqlScript.dasApiCreateSqlScript.gmtModified
            .gt(new Date(time))
            .and(
                QDasApiCreateSqlScript.dasApiCreateSqlScript.accessMethod.eq(
                    CREATE_API_MYBATIS_SQL_SCRIPT_TYPE))
            .and(QDasApiCreateSqlScript.dasApiCreateSqlScript.delFlag.eq(0))
            .and(QDasApiBasicInfo.dasApiBasicInfo.delFlag.eq(0));
    return dataQueryInfo(whereCondition);
  }

  private List<DataQueryInfoVO> dataQueryInfo(BooleanExpression whereCondition) {
    return jpaQueryFactory
        .select(QDasApiCreateSqlScript.dasApiCreateSqlScript, QDasApiBasicInfo.dasApiBasicInfo)
        .from(QDasApiCreateSqlScript.dasApiCreateSqlScript)
        .leftJoin(QDasApiBasicInfo.dasApiBasicInfo)
        .on(
            QDasApiCreateSqlScript.dasApiCreateSqlScript.apiId.eq(
                QDasApiBasicInfo.dasApiBasicInfo.apiId))
        .where(whereCondition)
        .fetch()
        .stream()
        .map(
            tuple ->
                new DataQueryInfoVO(
                    DasApiCreateSqlScriptMapper.INSTANCE.useDasApiCreateSqlScriptDefinitionVO(
                        tuple.get(QDasApiCreateSqlScript.dasApiCreateSqlScript)),
                    DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(
                        tuple.get(QDasApiBasicInfo.dasApiBasicInfo))))
        .collect(Collectors.toList());
  }
}
