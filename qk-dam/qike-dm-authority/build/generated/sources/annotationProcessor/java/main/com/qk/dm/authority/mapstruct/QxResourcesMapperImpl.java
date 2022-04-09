package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QxResources;
import com.qk.dm.authority.vo.powervo.ResourceExcelVO;
import com.qk.dm.authority.vo.powervo.ResourceOutVO;
import com.qk.dm.authority.vo.powervo.ResourceQueryVO;
import com.qk.dm.authority.vo.powervo.ResourceVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:52+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class QxResourcesMapperImpl implements QxResourcesMapper {

    @Override
    public QxResources qxResources(ResourceVO resourceVO) {
        if ( resourceVO == null ) {
            return null;
        }

        QxResources qxResources = new QxResources();

        if ( resourceVO.getId() != null ) {
            qxResources.setId( resourceVO.getId() );
        }
        if ( resourceVO.getName() != null ) {
            qxResources.setName( resourceVO.getName() );
        }
        if ( resourceVO.getPath() != null ) {
            qxResources.setPath( resourceVO.getPath() );
        }
        if ( resourceVO.getDescription() != null ) {
            qxResources.setDescription( resourceVO.getDescription() );
        }
        if ( resourceVO.getCreateUserid() != null ) {
            qxResources.setCreateUserid( resourceVO.getCreateUserid() );
        }
        if ( resourceVO.getUpdateUserid() != null ) {
            qxResources.setUpdateUserid( resourceVO.getUpdateUserid() );
        }
        if ( resourceVO.getCreateName() != null ) {
            qxResources.setCreateName( resourceVO.getCreateName() );
        }
        if ( resourceVO.getUpdateName() != null ) {
            qxResources.setUpdateName( resourceVO.getUpdateName() );
        }
        if ( resourceVO.getGmtCreate() != null ) {
            qxResources.setGmtCreate( resourceVO.getGmtCreate() );
        }
        if ( resourceVO.getGmtModified() != null ) {
            qxResources.setGmtModified( resourceVO.getGmtModified() );
        }
        if ( resourceVO.getPid() != null ) {
            qxResources.setPid( resourceVO.getPid() );
        }
        if ( resourceVO.getServiceId() != null ) {
            qxResources.setServiceId( resourceVO.getServiceId() );
        }
        if ( resourceVO.getType() != null ) {
            qxResources.setType( resourceVO.getType() );
        }
        if ( resourceVO.getResourcesid() != null ) {
            qxResources.setResourcesid( resourceVO.getResourcesid() );
        }

        return qxResources;
    }

    @Override
    public List<ResourceOutVO> of(List<QxResources> list) {
        if ( list == null ) {
            return null;
        }

        List<ResourceOutVO> list1 = new ArrayList<ResourceOutVO>( list.size() );
        for ( QxResources qxResources : list ) {
            list1.add( qxResourcesToResourceOutVO( qxResources ) );
        }

        return list1;
    }

    @Override
    public List<ResourceVO> qxResourcesOf(List<QxResources> list) {
        if ( list == null ) {
            return null;
        }

        List<ResourceVO> list1 = new ArrayList<ResourceVO>( list.size() );
        for ( QxResources qxResources : list ) {
            list1.add( qxResourceVO( qxResources ) );
        }

        return list1;
    }

    @Override
    public ResourceVO qxResourceVO(QxResources qxResources) {
        if ( qxResources == null ) {
            return null;
        }

        ResourceVO resourceVO = new ResourceVO();

        if ( qxResources.getId() != null ) {
            resourceVO.setId( qxResources.getId() );
        }
        if ( qxResources.getName() != null ) {
            resourceVO.setName( qxResources.getName() );
        }
        if ( qxResources.getPath() != null ) {
            resourceVO.setPath( qxResources.getPath() );
        }
        if ( qxResources.getDescription() != null ) {
            resourceVO.setDescription( qxResources.getDescription() );
        }
        if ( qxResources.getCreateUserid() != null ) {
            resourceVO.setCreateUserid( qxResources.getCreateUserid() );
        }
        if ( qxResources.getUpdateUserid() != null ) {
            resourceVO.setUpdateUserid( qxResources.getUpdateUserid() );
        }
        if ( qxResources.getCreateName() != null ) {
            resourceVO.setCreateName( qxResources.getCreateName() );
        }
        if ( qxResources.getUpdateName() != null ) {
            resourceVO.setUpdateName( qxResources.getUpdateName() );
        }
        if ( qxResources.getGmtCreate() != null ) {
            resourceVO.setGmtCreate( qxResources.getGmtCreate() );
        }
        if ( qxResources.getGmtModified() != null ) {
            resourceVO.setGmtModified( qxResources.getGmtModified() );
        }
        if ( qxResources.getPid() != null ) {
            resourceVO.setPid( qxResources.getPid() );
        }
        if ( qxResources.getServiceId() != null ) {
            resourceVO.setServiceId( qxResources.getServiceId() );
        }
        if ( qxResources.getType() != null ) {
            resourceVO.setType( qxResources.getType() );
        }
        if ( qxResources.getResourcesid() != null ) {
            resourceVO.setResourcesid( qxResources.getResourcesid() );
        }

        return resourceVO;
    }

    @Override
    public QxResources qxResourcesExcel(ResourceExcelVO resourceExcelVO) {
        if ( resourceExcelVO == null ) {
            return null;
        }

        QxResources qxResources = new QxResources();

        if ( resourceExcelVO.getId() != null ) {
            qxResources.setId( resourceExcelVO.getId() );
        }
        if ( resourceExcelVO.getName() != null ) {
            qxResources.setName( resourceExcelVO.getName() );
        }
        if ( resourceExcelVO.getPath() != null ) {
            qxResources.setPath( resourceExcelVO.getPath() );
        }
        if ( resourceExcelVO.getDescription() != null ) {
            qxResources.setDescription( resourceExcelVO.getDescription() );
        }
        if ( resourceExcelVO.getCreateUserid() != null ) {
            qxResources.setCreateUserid( resourceExcelVO.getCreateUserid() );
        }
        if ( resourceExcelVO.getUpdateUserid() != null ) {
            qxResources.setUpdateUserid( resourceExcelVO.getUpdateUserid() );
        }
        if ( resourceExcelVO.getCreateName() != null ) {
            qxResources.setCreateName( resourceExcelVO.getCreateName() );
        }
        if ( resourceExcelVO.getUpdateName() != null ) {
            qxResources.setUpdateName( resourceExcelVO.getUpdateName() );
        }
        if ( resourceExcelVO.getGmtCreate() != null ) {
            qxResources.setGmtCreate( resourceExcelVO.getGmtCreate() );
        }
        if ( resourceExcelVO.getGmtModified() != null ) {
            qxResources.setGmtModified( resourceExcelVO.getGmtModified() );
        }
        if ( resourceExcelVO.getServiceId() != null ) {
            qxResources.setServiceId( resourceExcelVO.getServiceId() );
        }
        if ( resourceExcelVO.getType() != null ) {
            qxResources.setType( resourceExcelVO.getType() );
        }
        if ( resourceExcelVO.getResourcesid() != null ) {
            qxResources.setResourcesid( resourceExcelVO.getResourcesid() );
        }

        return qxResources;
    }

    @Override
    public List<QxResources> ofResourcesVO(List<ResourceVO> qxResourcesList) {
        if ( qxResourcesList == null ) {
            return null;
        }

        List<QxResources> list = new ArrayList<QxResources>( qxResourcesList.size() );
        for ( ResourceVO resourceVO : qxResourcesList ) {
            list.add( qxResources( resourceVO ) );
        }

        return list;
    }

    @Override
    public List<ResourceExcelVO> ofExcelVO(List<QxResources> list) {
        if ( list == null ) {
            return null;
        }

        List<ResourceExcelVO> list1 = new ArrayList<ResourceExcelVO>( list.size() );
        for ( QxResources qxResources : list ) {
            list1.add( qxResourcesToResourceExcelVO( qxResources ) );
        }

        return list1;
    }

    @Override
    public ResourceQueryVO resourceOutVOof(ResourceOutVO resourceOutVO) {
        if ( resourceOutVO == null ) {
            return null;
        }

        ResourceQueryVO resourceQueryVO = new ResourceQueryVO();

        if ( resourceOutVO.getResourcesid() != null ) {
            resourceQueryVO.setValue( resourceOutVO.getResourcesid() );
        }
        if ( resourceOutVO.getName() != null ) {
            resourceQueryVO.setTitle( resourceOutVO.getName() );
        }
        List<ResourceQueryVO> list = resourceOutVOlist( resourceOutVO.getChildrenList() );
        if ( list != null ) {
            resourceQueryVO.setChildren( list );
        }
        if ( resourceOutVO.getId() != null ) {
            resourceQueryVO.setId( resourceOutVO.getId() );
        }
        if ( resourceOutVO.getPath() != null ) {
            resourceQueryVO.setPath( resourceOutVO.getPath() );
        }
        if ( resourceOutVO.getDescription() != null ) {
            resourceQueryVO.setDescription( resourceOutVO.getDescription() );
        }
        if ( resourceOutVO.getCreateUserid() != null ) {
            resourceQueryVO.setCreateUserid( resourceOutVO.getCreateUserid() );
        }
        if ( resourceOutVO.getUpdateUserid() != null ) {
            resourceQueryVO.setUpdateUserid( resourceOutVO.getUpdateUserid() );
        }
        if ( resourceOutVO.getCreateName() != null ) {
            resourceQueryVO.setCreateName( resourceOutVO.getCreateName() );
        }
        if ( resourceOutVO.getUpdateName() != null ) {
            resourceQueryVO.setUpdateName( resourceOutVO.getUpdateName() );
        }
        if ( resourceOutVO.getGmtCreate() != null ) {
            resourceQueryVO.setGmtCreate( resourceOutVO.getGmtCreate() );
        }
        if ( resourceOutVO.getGmtModified() != null ) {
            resourceQueryVO.setGmtModified( resourceOutVO.getGmtModified() );
        }
        if ( resourceOutVO.getPid() != null ) {
            resourceQueryVO.setPid( resourceOutVO.getPid() );
        }
        if ( resourceOutVO.getServiceId() != null ) {
            resourceQueryVO.setServiceId( resourceOutVO.getServiceId() );
        }
        if ( resourceOutVO.getType() != null ) {
            resourceQueryVO.setType( resourceOutVO.getType() );
        }
        if ( resourceOutVO.getResourcesid() != null ) {
            resourceQueryVO.setResourcesid( resourceOutVO.getResourcesid() );
        }

        return resourceQueryVO;
    }

    @Override
    public List<ResourceQueryVO> resourceOutVOlist(List<ResourceOutVO> resourceOutVOList) {
        if ( resourceOutVOList == null ) {
            return null;
        }

        List<ResourceQueryVO> list = new ArrayList<ResourceQueryVO>( resourceOutVOList.size() );
        for ( ResourceOutVO resourceOutVO : resourceOutVOList ) {
            list.add( resourceOutVOof( resourceOutVO ) );
        }

        return list;
    }

    protected ResourceOutVO qxResourcesToResourceOutVO(QxResources qxResources) {
        if ( qxResources == null ) {
            return null;
        }

        ResourceOutVO resourceOutVO = new ResourceOutVO();

        if ( qxResources.getId() != null ) {
            resourceOutVO.setId( qxResources.getId() );
        }
        if ( qxResources.getName() != null ) {
            resourceOutVO.setName( qxResources.getName() );
        }
        if ( qxResources.getPath() != null ) {
            resourceOutVO.setPath( qxResources.getPath() );
        }
        if ( qxResources.getDescription() != null ) {
            resourceOutVO.setDescription( qxResources.getDescription() );
        }
        if ( qxResources.getCreateUserid() != null ) {
            resourceOutVO.setCreateUserid( qxResources.getCreateUserid() );
        }
        if ( qxResources.getUpdateUserid() != null ) {
            resourceOutVO.setUpdateUserid( qxResources.getUpdateUserid() );
        }
        if ( qxResources.getCreateName() != null ) {
            resourceOutVO.setCreateName( qxResources.getCreateName() );
        }
        if ( qxResources.getUpdateName() != null ) {
            resourceOutVO.setUpdateName( qxResources.getUpdateName() );
        }
        if ( qxResources.getGmtCreate() != null ) {
            resourceOutVO.setGmtCreate( qxResources.getGmtCreate() );
        }
        if ( qxResources.getGmtModified() != null ) {
            resourceOutVO.setGmtModified( qxResources.getGmtModified() );
        }
        if ( qxResources.getPid() != null ) {
            resourceOutVO.setPid( qxResources.getPid() );
        }
        if ( qxResources.getServiceId() != null ) {
            resourceOutVO.setServiceId( qxResources.getServiceId() );
        }
        if ( qxResources.getType() != null ) {
            resourceOutVO.setType( qxResources.getType() );
        }
        if ( qxResources.getResourcesid() != null ) {
            resourceOutVO.setResourcesid( qxResources.getResourcesid() );
        }

        return resourceOutVO;
    }

    protected ResourceExcelVO qxResourcesToResourceExcelVO(QxResources qxResources) {
        if ( qxResources == null ) {
            return null;
        }

        ResourceExcelVO resourceExcelVO = new ResourceExcelVO();

        if ( qxResources.getId() != null ) {
            resourceExcelVO.setId( qxResources.getId() );
        }
        if ( qxResources.getName() != null ) {
            resourceExcelVO.setName( qxResources.getName() );
        }
        if ( qxResources.getPath() != null ) {
            resourceExcelVO.setPath( qxResources.getPath() );
        }
        if ( qxResources.getDescription() != null ) {
            resourceExcelVO.setDescription( qxResources.getDescription() );
        }
        if ( qxResources.getCreateUserid() != null ) {
            resourceExcelVO.setCreateUserid( qxResources.getCreateUserid() );
        }
        if ( qxResources.getUpdateUserid() != null ) {
            resourceExcelVO.setUpdateUserid( qxResources.getUpdateUserid() );
        }
        if ( qxResources.getCreateName() != null ) {
            resourceExcelVO.setCreateName( qxResources.getCreateName() );
        }
        if ( qxResources.getUpdateName() != null ) {
            resourceExcelVO.setUpdateName( qxResources.getUpdateName() );
        }
        if ( qxResources.getGmtCreate() != null ) {
            resourceExcelVO.setGmtCreate( qxResources.getGmtCreate() );
        }
        if ( qxResources.getGmtModified() != null ) {
            resourceExcelVO.setGmtModified( qxResources.getGmtModified() );
        }
        if ( qxResources.getServiceId() != null ) {
            resourceExcelVO.setServiceId( qxResources.getServiceId() );
        }
        if ( qxResources.getType() != null ) {
            resourceExcelVO.setType( qxResources.getType() );
        }
        if ( qxResources.getResourcesid() != null ) {
            resourceExcelVO.setResourcesid( qxResources.getResourcesid() );
        }

        return resourceExcelVO;
    }
}
