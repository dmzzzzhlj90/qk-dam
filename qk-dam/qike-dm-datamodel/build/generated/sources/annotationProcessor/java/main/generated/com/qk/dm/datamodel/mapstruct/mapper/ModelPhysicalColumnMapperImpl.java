package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelPhysicalColumn;
import com.qk.dm.datamodel.params.dto.ModelPhysicalColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalColumnVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:39:39+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelPhysicalColumnMapperImpl implements ModelPhysicalColumnMapper {

    @Override
    public List<ModelPhysicalColumn> use(List<ModelPhysicalColumnDTO> modelPhysicalColumnDTOList) {
        if ( modelPhysicalColumnDTOList == null ) {
            return null;
        }

        List<ModelPhysicalColumn> list = new ArrayList<ModelPhysicalColumn>( modelPhysicalColumnDTOList.size() );
        for ( ModelPhysicalColumnDTO modelPhysicalColumnDTO : modelPhysicalColumnDTOList ) {
            list.add( modelPhysicalColumnDTOToModelPhysicalColumn( modelPhysicalColumnDTO ) );
        }

        return list;
    }

    @Override
    public ModelPhysicalColumnVO of(ModelPhysicalColumn modelPhysicalColumn) {
        if ( modelPhysicalColumn == null ) {
            return null;
        }

        ModelPhysicalColumnVO modelPhysicalColumnVO = new ModelPhysicalColumnVO();

        modelPhysicalColumnVO.setId( modelPhysicalColumn.getId() );
        modelPhysicalColumnVO.setTableId( modelPhysicalColumn.getTableId() );
        modelPhysicalColumnVO.setColumnName( modelPhysicalColumn.getColumnName() );
        modelPhysicalColumnVO.setColumnType( modelPhysicalColumn.getColumnType() );
        modelPhysicalColumnVO.setStandardsId( modelPhysicalColumn.getStandardsId() );
        modelPhysicalColumnVO.setStandardsName( modelPhysicalColumn.getStandardsName() );
        modelPhysicalColumnVO.setStandardsCode( modelPhysicalColumn.getStandardsCode() );
        modelPhysicalColumnVO.setItsPrimaryKey( modelPhysicalColumn.getItsPrimaryKey() );
        modelPhysicalColumnVO.setItsPartition( modelPhysicalColumn.getItsPartition() );
        modelPhysicalColumnVO.setItsNull( modelPhysicalColumn.getItsNull() );
        modelPhysicalColumnVO.setDescription( modelPhysicalColumn.getDescription() );
        modelPhysicalColumnVO.setGmtCreate( modelPhysicalColumn.getGmtCreate() );
        modelPhysicalColumnVO.setGmtModified( modelPhysicalColumn.getGmtModified() );

        return modelPhysicalColumnVO;
    }

    @Override
    public List<ModelPhysicalColumnVO> of(List<ModelPhysicalColumn> modelPhysicalColumnList) {
        if ( modelPhysicalColumnList == null ) {
            return null;
        }

        List<ModelPhysicalColumnVO> list = new ArrayList<ModelPhysicalColumnVO>( modelPhysicalColumnList.size() );
        for ( ModelPhysicalColumn modelPhysicalColumn : modelPhysicalColumnList ) {
            list.add( of( modelPhysicalColumn ) );
        }

        return list;
    }

    @Override
    public void from(ModelPhysicalColumnDTO modelPhysicalColumnDTO, ModelPhysicalColumn modelPhysicalColumn) {
        if ( modelPhysicalColumnDTO == null ) {
            return;
        }

        if ( modelPhysicalColumnDTO.getTableId() != null ) {
            modelPhysicalColumn.setTableId( modelPhysicalColumnDTO.getTableId() );
        }
        if ( modelPhysicalColumnDTO.getColumnName() != null ) {
            modelPhysicalColumn.setColumnName( modelPhysicalColumnDTO.getColumnName() );
        }
        if ( modelPhysicalColumnDTO.getColumnType() != null ) {
            modelPhysicalColumn.setColumnType( modelPhysicalColumnDTO.getColumnType() );
        }
        if ( modelPhysicalColumnDTO.getStandardsId() != null ) {
            modelPhysicalColumn.setStandardsId( modelPhysicalColumnDTO.getStandardsId() );
        }
        if ( modelPhysicalColumnDTO.getStandardsName() != null ) {
            modelPhysicalColumn.setStandardsName( modelPhysicalColumnDTO.getStandardsName() );
        }
        if ( modelPhysicalColumnDTO.getStandardsCode() != null ) {
            modelPhysicalColumn.setStandardsCode( modelPhysicalColumnDTO.getStandardsCode() );
        }
        if ( modelPhysicalColumnDTO.getItsPrimaryKey() != null ) {
            modelPhysicalColumn.setItsPrimaryKey( modelPhysicalColumnDTO.getItsPrimaryKey() );
        }
        if ( modelPhysicalColumnDTO.getItsPartition() != null ) {
            modelPhysicalColumn.setItsPartition( modelPhysicalColumnDTO.getItsPartition() );
        }
        if ( modelPhysicalColumnDTO.getItsNull() != null ) {
            modelPhysicalColumn.setItsNull( modelPhysicalColumnDTO.getItsNull() );
        }
        if ( modelPhysicalColumnDTO.getDescription() != null ) {
            modelPhysicalColumn.setDescription( modelPhysicalColumnDTO.getDescription() );
        }
        if ( modelPhysicalColumnDTO.getGmtCreate() != null ) {
            modelPhysicalColumn.setGmtCreate( modelPhysicalColumnDTO.getGmtCreate() );
        }
        if ( modelPhysicalColumnDTO.getGmtModified() != null ) {
            modelPhysicalColumn.setGmtModified( modelPhysicalColumnDTO.getGmtModified() );
        }
    }

    protected ModelPhysicalColumn modelPhysicalColumnDTOToModelPhysicalColumn(ModelPhysicalColumnDTO modelPhysicalColumnDTO) {
        if ( modelPhysicalColumnDTO == null ) {
            return null;
        }

        ModelPhysicalColumn modelPhysicalColumn = new ModelPhysicalColumn();

        modelPhysicalColumn.setTableId( modelPhysicalColumnDTO.getTableId() );
        modelPhysicalColumn.setColumnName( modelPhysicalColumnDTO.getColumnName() );
        modelPhysicalColumn.setColumnType( modelPhysicalColumnDTO.getColumnType() );
        modelPhysicalColumn.setStandardsId( modelPhysicalColumnDTO.getStandardsId() );
        modelPhysicalColumn.setStandardsName( modelPhysicalColumnDTO.getStandardsName() );
        modelPhysicalColumn.setStandardsCode( modelPhysicalColumnDTO.getStandardsCode() );
        modelPhysicalColumn.setItsPrimaryKey( modelPhysicalColumnDTO.getItsPrimaryKey() );
        modelPhysicalColumn.setItsPartition( modelPhysicalColumnDTO.getItsPartition() );
        modelPhysicalColumn.setItsNull( modelPhysicalColumnDTO.getItsNull() );
        modelPhysicalColumn.setDescription( modelPhysicalColumnDTO.getDescription() );
        modelPhysicalColumn.setGmtCreate( modelPhysicalColumnDTO.getGmtCreate() );
        modelPhysicalColumn.setGmtModified( modelPhysicalColumnDTO.getGmtModified() );

        return modelPhysicalColumn;
    }
}
