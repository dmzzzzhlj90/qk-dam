package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.AtyRealmService;
import com.qk.dm.authority.vo.ClientVO;
import com.qk.dm.authority.vo.RealmVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限管理_域
 *
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

    /**
     * 域管理-域列表
     *
     * @return DefaultCommonResult<List < RealmVO>>
     */
    @GetMapping("")
    public DefaultCommonResult<List<RealmVO>> getRealmList() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRealmService.getRealmList());
    }

    /**
     * 域管理-客户端列表
     *
     * @param realm
     * @param client_clientId 客户端 clientId
     * @return DefaultCommonResult<List < ClientVO>>
     */
    @GetMapping("/clients")
    public DefaultCommonResult<List<ClientVO>> getClientList(@Valid @NotNull String realm, String client_clientId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, atyRealmService.getClientList(realm, client_clientId));
    }
}
