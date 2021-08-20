package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasFlowStrategy;
import com.qk.dm.dataservice.vo.DasFlowStrategyVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * api服务流控vo转mapper
 *
 * @author zys
 * @date 2021/8/18
 * @since 1.0.0
 */
@Mapper
public interface DasFlowStrategyMapper {
  DasFlowStrategyMapper INSTANCE = Mappers.getMapper(DasFlowStrategyMapper.class);

  DasFlowStrategyVO useDasFlowStrategyVO(DasFlowStrategy dasFlowStrategy);

  DasFlowStrategy useDasFlowStrategy(DasFlowStrategyVO dasFlowStrategyVO);
}
