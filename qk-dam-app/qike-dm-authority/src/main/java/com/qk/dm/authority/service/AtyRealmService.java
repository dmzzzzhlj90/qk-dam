package com.qk.dm.authority.service;

import com.qk.dam.authority.common.vo.ClientVO;
import com.qk.dam.authority.common.vo.RealmVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 15:21
 * @since 1.0.0
 */
public interface AtyRealmService {
    List<RealmVO> getRealmList();

    List<ClientVO> getClientList(String realm, String client_clientId);
}
