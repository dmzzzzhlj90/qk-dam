package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.vo.user.*;
import org.keycloak.representations.idm.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/2/23 16:35
 * @since 1.0.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AtyUserMapper {
    AtyUserMapper INSTANCE = Mappers.getMapper(AtyUserMapper.class);

    AtyUserInfoVO userInfo(UserRepresentation userRepresentation);

    List<AtyUserInfoVO> userInfo(List<UserRepresentation> userRepresentation);

    UserRepresentation userInfo(AtyUserKeyCloakVO userVO);

    AtyUserKeyCloakVO userInfo(AtyUserCreateVO userVO);

    AtyUserKeyCloakVO userInfo(AtyUserUpdateVO atyUserVO);

    void userUpdate(AtyUserKeyCloakVO userVO, @MappingTarget UserRepresentation userRepresentation);

    void userExcel(AtyUserInfoVO atyUserInfoVO, @MappingTarget AtyUserExcelVO atyUserExcelVO);

  UserRepresentation userExcelInfo(AtyUserInputExceVO atyUserInputExceVO);
}
