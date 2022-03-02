package com.qk.dm.authority.service.impl;

import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyGroupService;
import com.qk.dm.authority.vo.group.AtyGroupCreateVO;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2022/3/2 17:44
 * @since 1.0.0
 */
@Service
public class AtyGroupServiceImpl implements AtyGroupService {

    private final KeyCloakApi keyCloakApi;

    public AtyGroupServiceImpl(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    @Override
    public void addGroup(AtyGroupCreateVO groupVO) {
        keyCloakApi.addGroup(groupVO.getRealm(),groupVO.getGroupName());
    }

    @Override
    public void updateGroup(String groupId, AtyGroupCreateVO groupVO) {
        keyCloakApi.updateGroup(groupVO.getRealm(),groupId,groupVO.getGroupName());
    }

    @Override
    public void deleteGroup(String groupId, String realm) {
        keyCloakApi.deleteGroup(realm,groupId);
    }
}
