package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelFactColumn;
import com.qk.dm.datamodel.params.dto.ModelFactColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelFactColumnVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:39:39+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelFactColumnMapperImpl implements ModelFactColumnMapper {

    @Override
    public List<ModelFactColumn> use(List<ModelFactColumnDTO> modelFactColumnDTOList) {
        if ( modelFactColumnDTOList == null ) {
            return null;
        }

        List<ModelFactColumn> list = new ArrayList<ModelFactColumn>( modelFactColumnDTOList.size() );
        for ( ModelFactColumnDTO modelFactColumnDTO : modelFactColumnDTOList ) {
            list.add( modelFactColumnDTOToModelFactColumn( modelFactColumnDTO ) );
        }

        return list;
    }

    @Override
    public ModelFactColumnVO of(ModelFactColumn modelFactColumn) {
        if ( modelFactColumn == null ) {
            return null;
        }

        ModelFactColumnVO modelFactColumnVO = new ModelFactColumnVO();

        modelFactColumnVO.setId( modelFactColumn.getId() );
        modelFactColumnVO.setFactId( modelFactColumn.getFactId() );
        modelFactColumnVO.setColumnName( modelFactColumn.getColumnName() );
        modelFactColumnVO.setColumnType( modelFactColumn.getColumnType() );
        modelFactColumnVO.setStandardsId( modelFactColumn.getStandardsId() );
        modelFactColumnVO.setStandardsName( modelFactColumn.getStandardsName() );
        modelFactColumnVO.setItsPrimaryKey( modelFactColumn.getItsPrimaryKey() );
        modelFactColumnVO.setItsPartition( modelFactColumn.getItsPartition() );
        modelFactColumnVO.setItsNull( modelFactColumn.getItsNull() );
        modelFactColumnVO.setDescription( modelFactColumn.getDescription() );
        modelFactColumnVO.setGmtCreate( modelFactColumn.getGmtCreate() );

        return modelFactColumnVO;
    }

    @Override
    public List<ModelFactColumnVO> of(List<ModelFactColumn> modelFactColumnList) {
        if ( modelFactColumnList == null ) {
            return null;
        }

        List<ModelFactColumnVO> list = new ArrayList<ModelFactColumnVO>( modelFactColumnList.size() );
        for ( ModelFactColumn modelFactColumn : modelFactColumnList ) {
            list.add( of( modelFactColumn ) );
        }

        return list;
    }

    protected ModelFactColumn modelFactColumnDTOToModelFactColumn(ModelFactColumnDTO modelFactColumnDTO) {
        if ( modelFactColumnDTO == null ) {
            return null;
        }

        ModelFactColumn modelFactColumn = new ModelFactColumn();

        modelFactColumn.setId( modelFactColumnDTO.getId() );
        modelFactColumn.setFactId( modelFactColumnDTO.getFactId() );
        modelFactColumn.setColumnName( modelFactColumnDTO.getColumnName() );
        modelFactColumn.setColumnType( modelFactColumnDTO.getColumnType() );
        modelFactColumn.setStandardsId( modelFactColumnDTO.getStandardsId() );
        modelFactColumn.setStandardsName( modelFactColumnDTO.getStandardsName() );
        modelFactColumn.setItsPrimaryKey( modelFactColumnDTO.getItsPrimaryKey() );
        modelFactColumn.setItsPartition( modelFactColumnDTO.getItsPartition() );
        modelFactColumn.setItsNull( modelFactColumnDTO.getItsNull() );
        modelFactColumn.setDescription( modelFactColumnDTO.getDescription() );

        return modelFactColumn;
    }
}
