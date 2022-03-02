package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.AtyRealmService;
import com.qk.dm.authority.vo.ClientVO;
import com.qk.dm.authority.vo.RealmVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/2 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/realm")
public class AtyRealmController {
    private final AtyRealmService atyRealmService;

    public AtyRealmController(AtyRealmService atyRealmService) {
        this.atyRealmService = atyRealmService;
    }

    @GetMapping("")
    public DefaultCommonResult<List<RealmVO>> getRealmList(){
        return DefaultCommonResult.success(ResultCodeEnum.OK,atyRealmService.getRealmList());
    }

    @GetMapping("/clients")
    public DefaultCommonResult<List<ClientVO>> getClientList(String realm, String client_clientId){
        return DefaultCommonResult.success(ResultCodeEnum.OK,atyRealmService.getClientList(realm,client_clientId));
    }
}
