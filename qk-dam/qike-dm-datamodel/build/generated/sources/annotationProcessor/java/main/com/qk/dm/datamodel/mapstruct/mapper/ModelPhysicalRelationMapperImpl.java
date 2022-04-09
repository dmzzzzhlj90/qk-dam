package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelPhysicalRelation;
import com.qk.dm.datamodel.params.dto.ModelPhysicalRelationDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalRelationVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:48+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelPhysicalRelationMapperImpl implements ModelPhysicalRelationMapper {

    @Override
    public List<ModelPhysicalRelation> use(List<ModelPhysicalRelationDTO> modelPhysicalRelationDTOList) {
        if ( modelPhysicalRelationDTOList == null ) {
            return null;
        }

        List<ModelPhysicalRelation> list = new ArrayList<ModelPhysicalRelation>( modelPhysicalRelationDTOList.size() );
        for ( ModelPhysicalRelationDTO modelPhysicalRelationDTO : modelPhysicalRelationDTOList ) {
            list.add( modelPhysicalRelationDTOToModelPhysicalRelation( modelPhysicalRelationDTO ) );
        }

        return list;
    }

    @Override
    public ModelPhysicalRelationVO of(ModelPhysicalRelation modelPhysicalRelation) {
        if ( modelPhysicalRelation == null ) {
            return null;
        }

        ModelPhysicalRelationVO modelPhysicalRelationVO = new ModelPhysicalRelationVO();

        modelPhysicalRelationVO.setId( modelPhysicalRelation.getId() );
        modelPhysicalRelationVO.setTableId( modelPhysicalRelation.getTableId() );
        modelPhysicalRelationVO.setColumnName( modelPhysicalRelation.getColumnName() );
        modelPhysicalRelationVO.setChildTableName( modelPhysicalRelation.getChildTableName() );
        modelPhysicalRelationVO.setChildTableColumn( modelPhysicalRelation.getChildTableColumn() );
        modelPhysicalRelationVO.setChildConnectionWay( modelPhysicalRelation.getChildConnectionWay() );
        modelPhysicalRelationVO.setFatherTableName( modelPhysicalRelation.getFatherTableName() );
        modelPhysicalRelationVO.setFatherTableColumn( modelPhysicalRelation.getFatherTableColumn() );
        modelPhysicalRelationVO.setFatherConnectionWay( modelPhysicalRelation.getFatherConnectionWay() );
        modelPhysicalRelationVO.setGmtCreate( modelPhysicalRelation.getGmtCreate() );
        modelPhysicalRelationVO.setGmtModified( modelPhysicalRelation.getGmtModified() );

        return modelPhysicalRelationVO;
    }

    @Override
    public List<ModelPhysicalRelationVO> of(List<ModelPhysicalRelation> modelPhysicalRelationList) {
        if ( modelPhysicalRelationList == null ) {
            return null;
        }

        List<ModelPhysicalRelationVO> list = new ArrayList<ModelPhysicalRelationVO>( modelPhysicalRelationList.size() );
        for ( ModelPhysicalRelation modelPhysicalRelation : modelPhysicalRelationList ) {
            list.add( of( modelPhysicalRelation ) );
        }

        return list;
    }

    @Override
    public void from(ModelPhysicalRelationDTO modelPhysicalRelationDTO, ModelPhysicalRelation modelPhysicalRelation) {
        if ( modelPhysicalRelationDTO == null ) {
            return;
        }

        if ( modelPhysicalRelationDTO.getId() != null ) {
            modelPhysicalRelation.setId( modelPhysicalRelationDTO.getId() );
        }
        if ( modelPhysicalRelationDTO.getTableId() != null ) {
            modelPhysicalRelation.setTableId( modelPhysicalRelationDTO.getTableId() );
        }
        if ( modelPhysicalRelationDTO.getColumnName() != null ) {
            modelPhysicalRelation.setColumnName( modelPhysicalRelationDTO.getColumnName() );
        }
        if ( modelPhysicalRelationDTO.getChildTableName() != null ) {
            modelPhysicalRelation.setChildTableName( modelPhysicalRelationDTO.getChildTableName() );
        }
        if ( modelPhysicalRelationDTO.getChildTableColumn() != null ) {
            modelPhysicalRelation.setChildTableColumn( modelPhysicalRelationDTO.getChildTableColumn() );
        }
        if ( modelPhysicalRelationDTO.getChildConnectionWay() != null ) {
            modelPhysicalRelation.setChildConnectionWay( modelPhysicalRelationDTO.getChildConnectionWay() );
        }
        if ( modelPhysicalRelationDTO.getFatherTableName() != null ) {
            modelPhysicalRelation.setFatherTableName( modelPhysicalRelationDTO.getFatherTableName() );
        }
        if ( modelPhysicalRelationDTO.getFatherTableColumn() != null ) {
            modelPhysicalRelation.setFatherTableColumn( modelPhysicalRelationDTO.getFatherTableColumn() );
        }
        if ( modelPhysicalRelationDTO.getFatherConnectionWay() != null ) {
            modelPhysicalRelation.setFatherConnectionWay( modelPhysicalRelationDTO.getFatherConnectionWay() );
        }
        if ( modelPhysicalRelationDTO.getGmtCreate() != null ) {
            modelPhysicalRelation.setGmtCreate( modelPhysicalRelationDTO.getGmtCreate() );
        }
        if ( modelPhysicalRelationDTO.getGmtModified() != null ) {
            modelPhysicalRelation.setGmtModified( modelPhysicalRelationDTO.getGmtModified() );
        }
    }

    protected ModelPhysicalRelation modelPhysicalRelationDTOToModelPhysicalRelation(ModelPhysicalRelationDTO modelPhysicalRelationDTO) {
        if ( modelPhysicalRelationDTO == null ) {
            return null;
        }

        ModelPhysicalRelation modelPhysicalRelation = new ModelPhysicalRelation();

        modelPhysicalRelation.setId( modelPhysicalRelationDTO.getId() );
        modelPhysicalRelation.setTableId( modelPhysicalRelationDTO.getTableId() );
        modelPhysicalRelation.setColumnName( modelPhysicalRelationDTO.getColumnName() );
        modelPhysicalRelation.setChildTableName( modelPhysicalRelationDTO.getChildTableName() );
        modelPhysicalRelation.setChildTableColumn( modelPhysicalRelationDTO.getChildTableColumn() );
        modelPhysicalRelation.setChildConnectionWay( modelPhysicalRelationDTO.getChildConnectionWay() );
        modelPhysicalRelation.setFatherTableName( modelPhysicalRelationDTO.getFatherTableName() );
        modelPhysicalRelation.setFatherTableColumn( modelPhysicalRelationDTO.getFatherTableColumn() );
        modelPhysicalRelation.setFatherConnectionWay( modelPhysicalRelationDTO.getFatherConnectionWay() );
        modelPhysicalRelation.setGmtCreate( modelPhysicalRelationDTO.getGmtCreate() );
        modelPhysicalRelation.setGmtModified( modelPhysicalRelationDTO.getGmtModified() );

        return modelPhysicalRelation;
    }
}
