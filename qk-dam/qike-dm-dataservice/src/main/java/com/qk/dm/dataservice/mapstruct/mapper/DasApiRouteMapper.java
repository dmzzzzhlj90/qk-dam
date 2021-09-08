package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiRoute;
import com.qk.dm.dataservice.vo.DasApiRouteVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * API路由转换mapper
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Mapper
public interface DasApiRouteMapper {
    DasApiRouteMapper INSTANCE = Mappers.getMapper(DasApiRouteMapper.class);

    DasApiRoute useDasApiRoute(DasApiRouteVO dasApiRouteVO);

    DasApiRouteVO useDasApiRouteVO(DasApiRoute dasApiRoute);

}
