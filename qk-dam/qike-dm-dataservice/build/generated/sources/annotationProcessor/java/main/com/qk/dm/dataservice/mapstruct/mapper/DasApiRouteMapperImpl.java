package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiRoute;
import com.qk.dm.dataservice.vo.DasApiRouteVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:53+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DasApiRouteMapperImpl implements DasApiRouteMapper {

    @Override
    public DasApiRoute useDasApiRoute(DasApiRouteVO dasApiRouteVO) {
        if ( dasApiRouteVO == null ) {
            return null;
        }

        DasApiRoute dasApiRoute = new DasApiRoute();

        dasApiRoute.setId( dasApiRouteVO.getId() );
        dasApiRoute.setApiRouteId( dasApiRouteVO.getApiRouteId() );
        dasApiRoute.setApiRoutePath( dasApiRouteVO.getApiRoutePath() );
        dasApiRoute.setDescription( dasApiRouteVO.getDescription() );
        dasApiRoute.setGmtModified( dasApiRouteVO.getGmtModified() );

        return dasApiRoute;
    }

    @Override
    public DasApiRouteVO useDasApiRouteVO(DasApiRoute dasApiRoute) {
        if ( dasApiRoute == null ) {
            return null;
        }

        DasApiRouteVO dasApiRouteVO = new DasApiRouteVO();

        dasApiRouteVO.setId( dasApiRoute.getId() );
        dasApiRouteVO.setApiRouteId( dasApiRoute.getApiRouteId() );
        dasApiRouteVO.setApiRoutePath( dasApiRoute.getApiRoutePath() );
        dasApiRouteVO.setDescription( dasApiRoute.getDescription() );
        dasApiRouteVO.setGmtModified( dasApiRoute.getGmtModified() );

        return dasApiRouteVO;
    }
}
