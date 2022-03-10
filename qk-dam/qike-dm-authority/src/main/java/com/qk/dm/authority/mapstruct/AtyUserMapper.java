package com.qk.dm.authority.mapstruct;

import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserKeyCloakVO;
import com.qk.dm.authority.vo.user.AtyUserCreateVO;
import com.qk.dm.authority.vo.user.AtyUserExcelVO;
import com.qk.dm.authority.vo.user.AtyUserUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author shenpj
 * @date 2022/2/23 16:35
 * @since 1.0.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AtyUserMapper {
    AtyUserMapper INSTANCE = Mappers.getMapper(AtyUserMapper.class);

//    @Mapping(target = "createdTimestamp", expression = "java(com.qk.dm.authority.util.TimeUtil.parseLong(userRepresentation.getCreatedTimestamp()))")
//    AtyUserInfoVO userInfo(UserRepresentation userRepresentation);
//
//    @Mapping(target = "createdTimestamp", expression = "java(com.qk.dm.authority.util.TimeUtil.parseLong(userRepresentation.getCreatedTimestamp()))")
//    List<AtyUserInfoVO> userInfo(List<UserRepresentation> userRepresentation);
//
//    UserRepresentation userInfo(AtyUserKeyCloakVO userVO);

    AtyUserKeyCloakVO userInfo(AtyUserCreateVO userVO);

    AtyUserKeyCloakVO userInfo(AtyUserUpdateVO atyUserVO);

//    void userUpdate(AtyUserKeyCloakVO userVO, @MappingTarget UserRepresentation userRepresentation);

    void userExcel(AtyUserInfoVO atyUserInfoVO, @MappingTarget AtyUserExcelVO atyUserExcelVO);

//    UserRepresentation userExcelInfo(AtyUserInputExceVO atyUserInputExceVO);

}
