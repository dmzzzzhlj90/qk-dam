package com.qk.dam.authority.common.mapstruct;

import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dam.authority.common.vo.user.AtyUserKeyCloakVO;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Mapping(target = "createdTimestamp", expression = "java(com.qk.dam.authority.common.mapstruct.AtyUserMapper.parseLong(userRepresentation.getCreatedTimestamp()))")
    AtyUserInfoVO userInfo(UserRepresentation userRepresentation);

    @Mapping(target = "createdTimestamp", expression = "java(com.qk.dam.authority.common.mapstruct.AtyUserMapper.parseLong(userRepresentation.getCreatedTimestamp()))")
    List<AtyUserInfoVO> userInfo(List<UserRepresentation> userRepresentation);

    UserRepresentation userInfo(AtyUserKeyCloakVO userVO);

    void userUpdate(AtyUserKeyCloakVO userVO, @MappingTarget UserRepresentation userRepresentation);

    void userUpdate(UserRepresentation userVO, @MappingTarget UserRepresentation userRepresentation);

    UserRepresentation userExcelInfo(AtyUserInputExceVO atyUserInputExceVO);

    /**
     * 根据格式，Long 转 string
     */
    static String parseLong(Long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.format(date);
        } catch (Exception e) {
            return null;
        }
    }
}
