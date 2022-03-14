package com.qk.dam.authority.common.keycloak;

import com.qk.dam.authority.common.mapstruct.KeyCloakMapper;
import com.qk.dam.authority.common.vo.ClientVO;
import com.qk.dam.authority.common.vo.RealmVO;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/9 17:33
 * @since 1.0.0
 */
@Service
public class KeyCloakRealmApi {
    private final KeyCloakApi keyCloakApi;

    public KeyCloakRealmApi(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    /**
     * 所有域
     *
     * @return
     */
    public List<RealmVO> realmList() {
        return KeyCloakMapper.INSTANCE.userRealm(keyCloakApi.getRealmRepresentationList());
    }

    /**
     * 域下客户端列表
     *
     * @param realm
     * @param client_clientId
     * @return
     */
    public List<ClientVO> clientList(String realm, String client_clientId) {
        List<ClientRepresentation> list = keyCloakApi.getClientRepresentationList(realm, client_clientId);
        return KeyCloakMapper.INSTANCE.userClient(list);
    }
}
