package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QxEmpower;
import com.qk.dm.authority.vo.powervo.EmpowerVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:52+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class QxEmpowerMapperImpl implements QxEmpowerMapper {

    @Override
    public QxEmpower qxEmpower(EmpowerVO empowerVO) {
        if ( empowerVO == null ) {
            return null;
        }

        QxEmpower qxEmpower = new QxEmpower();

        if ( empowerVO.getId() != null ) {
            qxEmpower.setId( empowerVO.getId() );
        }
        if ( empowerVO.getEmpoerType() != null ) {
            qxEmpower.setEmpoerType( empowerVO.getEmpoerType() );
        }
        if ( empowerVO.getEmpoerName() != null ) {
            qxEmpower.setEmpoerName( empowerVO.getEmpoerName() );
        }
        if ( empowerVO.getEmpoerId() != null ) {
            qxEmpower.setEmpoerId( empowerVO.getEmpoerId() );
        }
        if ( empowerVO.getType() != null ) {
            qxEmpower.setType( empowerVO.getType() );
        }
        if ( empowerVO.getPowerType() != null ) {
            qxEmpower.setPowerType( empowerVO.getPowerType() );
        }
        if ( empowerVO.getCreateUserid() != null ) {
            qxEmpower.setCreateUserid( empowerVO.getCreateUserid() );
        }
        if ( empowerVO.getUpdateUserid() != null ) {
            qxEmpower.setUpdateUserid( empowerVO.getUpdateUserid() );
        }
        if ( empowerVO.getCreateUsername() != null ) {
            qxEmpower.setCreateUsername( empowerVO.getCreateUsername() );
        }
        if ( empowerVO.getUpdateUsername() != null ) {
            qxEmpower.setUpdateUsername( empowerVO.getUpdateUsername() );
        }
        if ( empowerVO.getGmtCreate() != null ) {
            qxEmpower.setGmtCreate( empowerVO.getGmtCreate() );
        }
        if ( empowerVO.getGmtModified() != null ) {
            qxEmpower.setGmtModified( empowerVO.getGmtModified() );
        }
        if ( empowerVO.getServiceId() != null ) {
            qxEmpower.setServiceId( empowerVO.getServiceId() );
        }
        if ( empowerVO.getEmpowerId() != null ) {
            qxEmpower.setEmpowerId( empowerVO.getEmpowerId() );
        }
        if ( empowerVO.getClientName() != null ) {
            qxEmpower.setClientName( empowerVO.getClientName() );
        }

        return qxEmpower;
    }

    @Override
    public EmpowerVO qxEmpowerVO(QxEmpower qxEmpower) {
        if ( qxEmpower == null ) {
            return null;
        }

        EmpowerVO empowerVO = new EmpowerVO();

        if ( qxEmpower.getId() != null ) {
            empowerVO.setId( qxEmpower.getId() );
        }
        if ( qxEmpower.getEmpoerType() != null ) {
            empowerVO.setEmpoerType( qxEmpower.getEmpoerType() );
        }
        if ( qxEmpower.getEmpoerName() != null ) {
            empowerVO.setEmpoerName( qxEmpower.getEmpoerName() );
        }
        if ( qxEmpower.getEmpoerId() != null ) {
            empowerVO.setEmpoerId( qxEmpower.getEmpoerId() );
        }
        if ( qxEmpower.getType() != null ) {
            empowerVO.setType( qxEmpower.getType() );
        }
        if ( qxEmpower.getPowerType() != null ) {
            empowerVO.setPowerType( qxEmpower.getPowerType() );
        }
        if ( qxEmpower.getCreateUserid() != null ) {
            empowerVO.setCreateUserid( qxEmpower.getCreateUserid() );
        }
        if ( qxEmpower.getUpdateUserid() != null ) {
            empowerVO.setUpdateUserid( qxEmpower.getUpdateUserid() );
        }
        if ( qxEmpower.getCreateUsername() != null ) {
            empowerVO.setCreateUsername( qxEmpower.getCreateUsername() );
        }
        if ( qxEmpower.getUpdateUsername() != null ) {
            empowerVO.setUpdateUsername( qxEmpower.getUpdateUsername() );
        }
        if ( qxEmpower.getGmtCreate() != null ) {
            empowerVO.setGmtCreate( qxEmpower.getGmtCreate() );
        }
        if ( qxEmpower.getGmtModified() != null ) {
            empowerVO.setGmtModified( qxEmpower.getGmtModified() );
        }
        if ( qxEmpower.getServiceId() != null ) {
            empowerVO.setServiceId( qxEmpower.getServiceId() );
        }
        if ( qxEmpower.getEmpowerId() != null ) {
            empowerVO.setEmpowerId( qxEmpower.getEmpowerId() );
        }
        if ( qxEmpower.getClientName() != null ) {
            empowerVO.setClientName( qxEmpower.getClientName() );
        }

        return empowerVO;
    }

    @Override
    public List<EmpowerVO> of(List<QxEmpower> list) {
        if ( list == null ) {
            return null;
        }

        List<EmpowerVO> list1 = new ArrayList<EmpowerVO>( list.size() );
        for ( QxEmpower qxEmpower : list ) {
            list1.add( qxEmpowerVO( qxEmpower ) );
        }

        return list1;
    }
}
