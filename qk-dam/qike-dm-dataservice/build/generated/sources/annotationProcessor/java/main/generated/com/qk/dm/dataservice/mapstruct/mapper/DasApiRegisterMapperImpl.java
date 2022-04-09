package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.vo.DasApiRegisterDefinitionVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:28+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DasApiRegisterMapperImpl implements DasApiRegisterMapper {

    @Override
    public DasApiRegisterDefinitionVO useDasApiRegisterDefinitioVO(DasApiRegister dasApiRegister) {
        if ( dasApiRegister == null ) {
            return null;
        }

        DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO = new DasApiRegisterDefinitionVO();

        dasApiRegisterDefinitionVO.setId( dasApiRegister.getId() );
        dasApiRegisterDefinitionVO.setApiId( dasApiRegister.getApiId() );
        dasApiRegisterDefinitionVO.setApiRouteId( dasApiRegister.getApiRouteId() );
        dasApiRegisterDefinitionVO.setProtocolType( dasApiRegister.getProtocolType() );
        dasApiRegisterDefinitionVO.setRequestType( dasApiRegister.getRequestType() );
        dasApiRegisterDefinitionVO.setBackendHost( dasApiRegister.getBackendHost() );
        dasApiRegisterDefinitionVO.setBackendPath( dasApiRegister.getBackendPath() );
        dasApiRegisterDefinitionVO.setBackendTimeout( dasApiRegister.getBackendTimeout() );
        dasApiRegisterDefinitionVO.setDescription( dasApiRegister.getDescription() );

        return dasApiRegisterDefinitionVO;
    }

    @Override
    public DasApiRegister useDasApiRegister(DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO) {
        if ( dasApiRegisterDefinitionVO == null ) {
            return null;
        }

        DasApiRegister dasApiRegister = new DasApiRegister();

        dasApiRegister.setId( dasApiRegisterDefinitionVO.getId() );
        dasApiRegister.setApiId( dasApiRegisterDefinitionVO.getApiId() );
        dasApiRegister.setApiRouteId( dasApiRegisterDefinitionVO.getApiRouteId() );
        dasApiRegister.setProtocolType( dasApiRegisterDefinitionVO.getProtocolType() );
        dasApiRegister.setRequestType( dasApiRegisterDefinitionVO.getRequestType() );
        dasApiRegister.setBackendHost( dasApiRegisterDefinitionVO.getBackendHost() );
        dasApiRegister.setBackendPath( dasApiRegisterDefinitionVO.getBackendPath() );
        dasApiRegister.setBackendTimeout( dasApiRegisterDefinitionVO.getBackendTimeout() );
        dasApiRegister.setDescription( dasApiRegisterDefinitionVO.getDescription() );

        return dasApiRegister;
    }
}
