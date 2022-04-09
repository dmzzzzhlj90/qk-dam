package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelDimTableColumn;
import com.qk.dm.datamodel.params.dto.ModelDimTableColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableColumnVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:47+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelDimTableColumnMapperImpl implements ModelDimTableColumnMapper {

    @Override
    public List<ModelDimTableColumn> use(List<ModelDimTableColumnDTO> modelDimTableColumnDTOList) {
        if ( modelDimTableColumnDTOList == null ) {
            return null;
        }

        List<ModelDimTableColumn> list = new ArrayList<ModelDimTableColumn>( modelDimTableColumnDTOList.size() );
        for ( ModelDimTableColumnDTO modelDimTableColumnDTO : modelDimTableColumnDTOList ) {
            list.add( modelDimTableColumnDTOToModelDimTableColumn( modelDimTableColumnDTO ) );
        }

        return list;
    }

    @Override
    public ModelDimTableColumnVO of(ModelDimTableColumn modelDimTableColumn) {
        if ( modelDimTableColumn == null ) {
            return null;
        }

        ModelDimTableColumnVO modelDimTableColumnVO = new ModelDimTableColumnVO();

        modelDimTableColumnVO.setId( modelDimTableColumn.getId() );
        modelDimTableColumnVO.setDimTableId( modelDimTableColumn.getDimTableId() );
        modelDimTableColumnVO.setColumnName( modelDimTableColumn.getColumnName() );
        modelDimTableColumnVO.setColumnType( modelDimTableColumn.getColumnType() );
        modelDimTableColumnVO.setStandardsId( modelDimTableColumn.getStandardsId() );
        modelDimTableColumnVO.setStandardsName( modelDimTableColumn.getStandardsName() );
        modelDimTableColumnVO.setItsPrimaryKey( modelDimTableColumn.getItsPrimaryKey() );
        modelDimTableColumnVO.setItsPartition( modelDimTableColumn.getItsPartition() );
        modelDimTableColumnVO.setItsNull( modelDimTableColumn.getItsNull() );
        modelDimTableColumnVO.setDescription( modelDimTableColumn.getDescription() );

        return modelDimTableColumnVO;
    }

    @Override
    public List<ModelDimTableColumnVO> of(List<ModelDimTableColumn> modelDimTableColumnList) {
        if ( modelDimTableColumnList == null ) {
            return null;
        }

        List<ModelDimTableColumnVO> list = new ArrayList<ModelDimTableColumnVO>( modelDimTableColumnList.size() );
        for ( ModelDimTableColumn modelDimTableColumn : modelDimTableColumnList ) {
            list.add( of( modelDimTableColumn ) );
        }

        return list;
    }

    protected ModelDimTableColumn modelDimTableColumnDTOToModelDimTableColumn(ModelDimTableColumnDTO modelDimTableColumnDTO) {
        if ( modelDimTableColumnDTO == null ) {
            return null;
        }

        ModelDimTableColumn modelDimTableColumn = new ModelDimTableColumn();

        modelDimTableColumn.setId( modelDimTableColumnDTO.getId() );
        modelDimTableColumn.setDimTableId( modelDimTableColumnDTO.getDimTableId() );
        modelDimTableColumn.setColumnName( modelDimTableColumnDTO.getColumnName() );
        modelDimTableColumn.setColumnType( modelDimTableColumnDTO.getColumnType() );
        modelDimTableColumn.setStandardsId( modelDimTableColumnDTO.getStandardsId() );
        modelDimTableColumn.setStandardsName( modelDimTableColumnDTO.getStandardsName() );
        modelDimTableColumn.setItsPrimaryKey( modelDimTableColumnDTO.getItsPrimaryKey() );
        modelDimTableColumn.setItsPartition( modelDimTableColumnDTO.getItsPartition() );
        modelDimTableColumn.setItsNull( modelDimTableColumnDTO.getItsNull() );
        modelDimTableColumn.setDescription( modelDimTableColumnDTO.getDescription() );

        return modelDimTableColumn;
    }
}
