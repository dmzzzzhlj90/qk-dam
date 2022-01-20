package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdLabels;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-20T14:27:54+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
public class MtdLabelsMapperImpl implements MtdLabelsMapper {

    @Override
    public MtdLabels useMtdLabels(MtdLabelsVO mtdLabelsVO) {
        if ( mtdLabelsVO == null ) {
            return null;
        }

        MtdLabels mtdLabels = new MtdLabels();

        mtdLabels.setName( mtdLabelsVO.getName() );
        mtdLabels.setDescription( mtdLabelsVO.getDescription() );

        return mtdLabels;
    }

    @Override
    public MtdLabelsInfoVO useMtdLabelsInfoVO(MtdLabels mtdLabels) {
        if ( mtdLabels == null ) {
            return null;
        }

        Long id = null;
        Date gmtCreate = null;

        id = mtdLabels.getId();
        gmtCreate = mtdLabels.getGmtCreate();

        MtdLabelsInfoVO mtdLabelsInfoVO = new MtdLabelsInfoVO( id, gmtCreate );

        mtdLabelsInfoVO.setName( mtdLabels.getName() );
        mtdLabelsInfoVO.setDescription( mtdLabels.getDescription() );

        return mtdLabelsInfoVO;
    }

    @Override
    public MtdLabelsListVO useMtdLabelsListVO(MtdLabelsVO mtdLabelsVO) {
        if ( mtdLabelsVO == null ) {
            return null;
        }

        MtdLabelsListVO mtdLabelsListVO = new MtdLabelsListVO();

        mtdLabelsListVO.setName( mtdLabelsVO.getName() );

        return mtdLabelsListVO;
    }

    @Override
    public void updateMtdLabelsVO(MtdLabelsVO mtdLabelsVO, MtdLabels mtdLabels) {
        if ( mtdLabelsVO == null ) {
            return;
        }

        mtdLabels.setName( mtdLabelsVO.getName() );
        mtdLabels.setDescription( mtdLabelsVO.getDescription() );
    }
}
