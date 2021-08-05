package com.qk.dm.datasource.mapstruct.mapper;

import com.qk.dm.datasource.entity.DsDatasource;
import com.qk.dm.datasource.vo.DsDatasourceVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * 数据源连接vo和实体相互转换mapper
 * @author zys
 * @date 20210729
 * @since 1.0.0
 */
@Mapper
public interface DSDatasourceMapper {
    DSDatasourceMapper INSTANCE = Mappers.getMapper(DSDatasourceMapper.class);

    DsDatasourceVO useDsDatasourceVO(DsDatasource dsDatasource);

    DsDatasource useDsDatasource(DsDatasourceVO dsDatasourceVO);
}
