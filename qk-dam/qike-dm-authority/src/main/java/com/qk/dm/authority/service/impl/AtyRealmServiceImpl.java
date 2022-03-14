package com.qk.dm.authority.service.impl;

import com.qk.dam.authority.common.keycloak.KeyCloakRealmApi;
import com.qk.dam.authority.common.vo.ClientVO;
import com.qk.dam.authority.common.vo.RealmVO;
import com.qk.dm.authority.service.AtyRealmService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 15:21
 * @since 1.0.0
 */
@Service
public class AtyRealmServiceImpl implements AtyRealmService {
    private final KeyCloakRealmApi keyCloakRealmApi;

    public AtyRealmServiceImpl(KeyCloakRealmApi keyCloakRealmApi) {
        this.keyCloakRealmApi = keyCloakRealmApi;
    }

    @Override
    public List<RealmVO> getRealmList() {
        return keyCloakRealmApi.realmList();
    }

    @Override
    public List<ClientVO> getClientList(String realm, String client_clientId) {
        return keyCloakRealmApi.clientList(realm,client_clientId);
    }
}
