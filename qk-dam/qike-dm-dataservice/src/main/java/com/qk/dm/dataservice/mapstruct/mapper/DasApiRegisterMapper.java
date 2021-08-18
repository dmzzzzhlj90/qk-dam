package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.vo.DasApiRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 注册API-vo转换mapper
 *
 * @author wjq
 * @date 20210817
 * @since 1.0.0
 */
@Mapper
public interface DasApiRegisterMapper {
  DasApiRegisterMapper INSTANCE = Mappers.getMapper(DasApiRegisterMapper.class);

  DasApiRegisterVO useDasApiRegisterVO(DasApiRegister dasApiRegister);

  DasApiRegister useDasApiRegister(DasApiRegisterVO dasApiRegisterVO);
}
