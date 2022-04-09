package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelDimColumn;
import com.qk.dm.datamodel.params.dto.ModelDimColumnDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelDimColumnVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:47+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelDimColumnMapperImpl implements ModelDimColumnMapper {

    @Override
    public ModelDimColumnVO of(ModelDimColumnDTO modelDimColumnDTO) {
        if ( modelDimColumnDTO == null ) {
            return null;
        }

        ModelDimColumnVO modelDimColumnVO = new ModelDimColumnVO();

        modelDimColumnVO.setId( modelDimColumnDTO.getId() );
        modelDimColumnVO.setDimId( modelDimColumnDTO.getDimId() );
        modelDimColumnVO.setColumnName( modelDimColumnDTO.getColumnName() );
        modelDimColumnVO.setColumnType( modelDimColumnDTO.getColumnType() );
        modelDimColumnVO.setStandardsId( modelDimColumnDTO.getStandardsId() );
        modelDimColumnVO.setStandardsName( modelDimColumnDTO.getStandardsName() );
        modelDimColumnVO.setItsPrimaryKey( modelDimColumnDTO.getItsPrimaryKey() );
        modelDimColumnVO.setItsPartition( modelDimColumnDTO.getItsPartition() );
        modelDimColumnVO.setItsNull( modelDimColumnDTO.getItsNull() );
        modelDimColumnVO.setDescription( modelDimColumnDTO.getDescription() );

        return modelDimColumnVO;
    }

    @Override
    public List<ModelDimColumn> use(List<ModelDimColumnDTO> modelDimColumnDTOList) {
        if ( modelDimColumnDTOList == null ) {
            return null;
        }

        List<ModelDimColumn> list = new ArrayList<ModelDimColumn>( modelDimColumnDTOList.size() );
        for ( ModelDimColumnDTO modelDimColumnDTO : modelDimColumnDTOList ) {
            list.add( modelDimColumnDTOToModelDimColumn( modelDimColumnDTO ) );
        }

        return list;
    }

    @Override
    public List<ModelDimTableColumnDTO> ofDimTableColumn(List<ModelDimColumnDTO> modelDimColumnList) {
        if ( modelDimColumnList == null ) {
            return null;
        }

        List<ModelDimTableColumnDTO> list = new ArrayList<ModelDimTableColumnDTO>( modelDimColumnList.size() );
        for ( ModelDimColumnDTO modelDimColumnDTO : modelDimColumnList ) {
            list.add( modelDimColumnDTOToModelDimTableColumnDTO( modelDimColumnDTO ) );
        }

        return list;
    }

    @Override
    public ModelDimColumnVO of(ModelDimColumn modelDimColumn) {
        if ( modelDimColumn == null ) {
            return null;
        }

        ModelDimColumnVO modelDimColumnVO = new ModelDimColumnVO();

        modelDimColumnVO.setId( modelDimColumn.getId() );
        modelDimColumnVO.setDimId( modelDimColumn.getDimId() );
        modelDimColumnVO.setColumnName( modelDimColumn.getColumnName() );
        modelDimColumnVO.setColumnType( modelDimColumn.getColumnType() );
        modelDimColumnVO.setStandardsId( modelDimColumn.getStandardsId() );
        modelDimColumnVO.setStandardsName( modelDimColumn.getStandardsName() );
        modelDimColumnVO.setItsPrimaryKey( modelDimColumn.getItsPrimaryKey() );
        modelDimColumnVO.setItsPartition( modelDimColumn.getItsPartition() );
        modelDimColumnVO.setItsNull( modelDimColumn.getItsNull() );
        modelDimColumnVO.setDescription( modelDimColumn.getDescription() );
        modelDimColumnVO.setGmtCreate( modelDimColumn.getGmtCreate() );
        modelDimColumnVO.setGmtModified( modelDimColumn.getGmtModified() );

        return modelDimColumnVO;
    }

    @Override
    public List<ModelDimColumnVO> of(List<ModelDimColumn> modelDimColumnList) {
        if ( modelDimColumnList == null ) {
            return null;
        }

        List<ModelDimColumnVO> list = new ArrayList<ModelDimColumnVO>( modelDimColumnList.size() );
        for ( ModelDimColumn modelDimColumn : modelDimColumnList ) {
            list.add( of( modelDimColumn ) );
        }

        return list;
    }

    protected ModelDimColumn modelDimColumnDTOToModelDimColumn(ModelDimColumnDTO modelDimColumnDTO) {
        if ( modelDimColumnDTO == null ) {
            return null;
        }

        ModelDimColumn modelDimColumn = new ModelDimColumn();

        modelDimColumn.setId( modelDimColumnDTO.getId() );
        modelDimColumn.setDimId( modelDimColumnDTO.getDimId() );
        modelDimColumn.setColumnName( modelDimColumnDTO.getColumnName() );
        modelDimColumn.setColumnType( modelDimColumnDTO.getColumnType() );
        modelDimColumn.setStandardsId( modelDimColumnDTO.getStandardsId() );
        modelDimColumn.setStandardsName( modelDimColumnDTO.getStandardsName() );
        modelDimColumn.setItsPrimaryKey( modelDimColumnDTO.getItsPrimaryKey() );
        modelDimColumn.setItsPartition( modelDimColumnDTO.getItsPartition() );
        modelDimColumn.setItsNull( modelDimColumnDTO.getItsNull() );
        modelDimColumn.setDescription( modelDimColumnDTO.getDescription() );

        return modelDimColumn;
    }

    protected ModelDimTableColumnDTO modelDimColumnDTOToModelDimTableColumnDTO(ModelDimColumnDTO modelDimColumnDTO) {
        if ( modelDimColumnDTO == null ) {
            return null;
        }

        ModelDimTableColumnDTO modelDimTableColumnDTO = new ModelDimTableColumnDTO();

        modelDimTableColumnDTO.setId( modelDimColumnDTO.getId() );
        modelDimTableColumnDTO.setColumnName( modelDimColumnDTO.getColumnName() );
        modelDimTableColumnDTO.setColumnType( modelDimColumnDTO.getColumnType() );
        modelDimTableColumnDTO.setStandardsId( modelDimColumnDTO.getStandardsId() );
        modelDimTableColumnDTO.setStandardsName( modelDimColumnDTO.getStandardsName() );
        modelDimTableColumnDTO.setItsPrimaryKey( modelDimColumnDTO.getItsPrimaryKey() );
        modelDimTableColumnDTO.setItsPartition( modelDimColumnDTO.getItsPartition() );
        modelDimTableColumnDTO.setItsNull( modelDimColumnDTO.getItsNull() );
        modelDimTableColumnDTO.setDescription( modelDimColumnDTO.getDescription() );

        return modelDimTableColumnDTO;
    }
}
