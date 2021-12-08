package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptBaseColumnInfo;
import com.qk.dm.reptile.params.dto.RptBaseInfoColumnDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
/**
 * 配置信息字段vo、实体转换
 */
public interface RptBaseInfoColumnMapper {
  RptBaseInfoColumnMapper INSTANCE = Mappers.getMapper(RptBaseInfoColumnMapper.class);

  RptBaseColumnInfo userRptBaseInfoColumnDTO(RptBaseInfoColumnDTO rptBaseInfoColumnDTO);

  RptBaseInfoColumnVO userRptBaseInfoColumnVO(RptBaseColumnInfo rptBaseColumnInfo);
}
