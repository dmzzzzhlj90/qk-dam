package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QxEmpower;
import com.qk.dm.authority.entity.QxService;
import com.qk.dm.authority.vo.powervo.EmpowerAllVO;
import com.qk.dm.authority.vo.powervo.ServiceVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:52+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class QxServiceMapperImpl implements QxServiceMapper {

    @Override
    public QxService qxService(ServiceVO serviceVO) {
        if ( serviceVO == null ) {
            return null;
        }

        QxService qxService = new QxService();

        if ( serviceVO.getId() != null ) {
            qxService.setId( serviceVO.getId() );
        }
        if ( serviceVO.getServiceName() != null ) {
            qxService.setServiceName( serviceVO.getServiceName() );
        }
        if ( serviceVO.getDescription() != null ) {
            qxService.setDescription( serviceVO.getDescription() );
        }
        if ( serviceVO.getCreateUserid() != null ) {
            qxService.setCreateUserid( serviceVO.getCreateUserid() );
        }
        if ( serviceVO.getUpdateUserid() != null ) {
            qxService.setUpdateUserid( serviceVO.getUpdateUserid() );
        }
        if ( serviceVO.getCreateUsername() != null ) {
            qxService.setCreateUsername( serviceVO.getCreateUsername() );
        }
        if ( serviceVO.getUpdateUsername() != null ) {
            qxService.setUpdateUsername( serviceVO.getUpdateUsername() );
        }
        if ( serviceVO.getGmtModified() != null ) {
            qxService.setGmtModified( serviceVO.getGmtModified() );
        }
        if ( serviceVO.getGmtCreate() != null ) {
            qxService.setGmtCreate( serviceVO.getGmtCreate() );
        }
        if ( serviceVO.getRedionid() != null ) {
            qxService.setRedionid( serviceVO.getRedionid() );
        }
        if ( serviceVO.getServiceid() != null ) {
            qxService.setServiceid( serviceVO.getServiceid() );
        }

        return qxService;
    }

    @Override
    public ServiceVO qxServiceVO(QxService qxService) {
        if ( qxService == null ) {
            return null;
        }

        ServiceVO serviceVO = new ServiceVO();

        if ( qxService.getServiceid() != null ) {
            serviceVO.setValue( qxService.getServiceid() );
        }
        if ( qxService.getServiceName() != null ) {
            serviceVO.setTitle( qxService.getServiceName() );
        }
        if ( qxService.getId() != null ) {
            serviceVO.setId( qxService.getId() );
        }
        if ( qxService.getServiceName() != null ) {
            serviceVO.setServiceName( qxService.getServiceName() );
        }
        if ( qxService.getDescription() != null ) {
            serviceVO.setDescription( qxService.getDescription() );
        }
        if ( qxService.getCreateUserid() != null ) {
            serviceVO.setCreateUserid( qxService.getCreateUserid() );
        }
        if ( qxService.getUpdateUserid() != null ) {
            serviceVO.setUpdateUserid( qxService.getUpdateUserid() );
        }
        if ( qxService.getCreateUsername() != null ) {
            serviceVO.setCreateUsername( qxService.getCreateUsername() );
        }
        if ( qxService.getUpdateUsername() != null ) {
            serviceVO.setUpdateUsername( qxService.getUpdateUsername() );
        }
        if ( qxService.getGmtModified() != null ) {
            serviceVO.setGmtModified( qxService.getGmtModified() );
        }
        if ( qxService.getGmtCreate() != null ) {
            serviceVO.setGmtCreate( qxService.getGmtCreate() );
        }
        if ( qxService.getRedionid() != null ) {
            serviceVO.setRedionid( qxService.getRedionid() );
        }
        if ( qxService.getServiceid() != null ) {
            serviceVO.setServiceid( qxService.getServiceid() );
        }

        return serviceVO;
    }

    @Override
    public List<ServiceVO> of(List<QxService> list) {
        if ( list == null ) {
            return null;
        }

        List<ServiceVO> list1 = new ArrayList<ServiceVO>( list.size() );
        for ( QxService qxService : list ) {
            list1.add( qxServiceVO( qxService ) );
        }

        return list1;
    }

    @Override
    public List<EmpowerAllVO> ofEmpowerAllVO(List<QxEmpower> qxEmpoerList) {
        if ( qxEmpoerList == null ) {
            return null;
        }

        List<EmpowerAllVO> list = new ArrayList<EmpowerAllVO>( qxEmpoerList.size() );
        for ( QxEmpower qxEmpower : qxEmpoerList ) {
            list.add( qxEmpowerToEmpowerAllVO( qxEmpower ) );
        }

        return list;
    }

    protected EmpowerAllVO qxEmpowerToEmpowerAllVO(QxEmpower qxEmpower) {
        if ( qxEmpower == null ) {
            return null;
        }

        EmpowerAllVO empowerAllVO = new EmpowerAllVO();

        if ( qxEmpower.getId() != null ) {
            empowerAllVO.setId( qxEmpower.getId() );
        }
        if ( qxEmpower.getEmpoerType() != null ) {
            empowerAllVO.setEmpoerType( qxEmpower.getEmpoerType() );
        }
        if ( qxEmpower.getEmpoerName() != null ) {
            empowerAllVO.setEmpoerName( qxEmpower.getEmpoerName() );
        }
        if ( qxEmpower.getEmpoerId() != null ) {
            empowerAllVO.setEmpoerId( qxEmpower.getEmpoerId() );
        }
        if ( qxEmpower.getType() != null ) {
            empowerAllVO.setType( qxEmpower.getType() );
        }
        if ( qxEmpower.getPowerType() != null ) {
            empowerAllVO.setPowerType( qxEmpower.getPowerType() );
        }
        if ( qxEmpower.getCreateUserid() != null ) {
            empowerAllVO.setCreateUserid( qxEmpower.getCreateUserid() );
        }
        if ( qxEmpower.getUpdateUserid() != null ) {
            empowerAllVO.setUpdateUserid( qxEmpower.getUpdateUserid() );
        }
        if ( qxEmpower.getCreateUsername() != null ) {
            empowerAllVO.setCreateUsername( qxEmpower.getCreateUsername() );
        }
        if ( qxEmpower.getUpdateUsername() != null ) {
            empowerAllVO.setUpdateUsername( qxEmpower.getUpdateUsername() );
        }
        if ( qxEmpower.getGmtCreate() != null ) {
            empowerAllVO.setGmtCreate( qxEmpower.getGmtCreate() );
        }
        if ( qxEmpower.getGmtModified() != null ) {
            empowerAllVO.setGmtModified( qxEmpower.getGmtModified() );
        }
        if ( qxEmpower.getServiceId() != null ) {
            empowerAllVO.setServiceId( qxEmpower.getServiceId() );
        }
        if ( qxEmpower.getEmpowerId() != null ) {
            empowerAllVO.setEmpowerId( qxEmpower.getEmpowerId() );
        }
        if ( qxEmpower.getClientName() != null ) {
            empowerAllVO.setClientName( qxEmpower.getClientName() );
        }

        return empowerAllVO;
    }
}
