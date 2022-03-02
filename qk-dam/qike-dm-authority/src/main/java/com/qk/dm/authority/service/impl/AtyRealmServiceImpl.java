package com.qk.dm.authority.service.impl;

import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.service.AtyRealmService;
import com.qk.dm.authority.vo.ClientVO;
import com.qk.dm.authority.vo.RealmVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 15:21
 * @since 1.0.0
 */
@Service
public class AtyRealmServiceImpl implements AtyRealmService {
    private final KeyCloakApi keyCloakApi;

    public AtyRealmServiceImpl(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    @Override
    public List<RealmVO> getRealmList() {
        return keyCloakApi.realmList();
    }

    @Override
    public List<ClientVO> getClientList(String realm, String client_clientId) {
        return keyCloakApi.clientListByRealm(realm,client_clientId);
    }
}
