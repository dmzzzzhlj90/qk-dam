package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;

import com.qk.dm.reptile.params.dto.RptUserDTO;
import com.qk.dm.reptile.params.vo.RptUserVO;
import com.qk.dm.reptile.service.RptUserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME;

/**
 * 爬虫用户信息
 * @author wangzp
 * @date 2022/02/5 11:18
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class RptUserController {

    private final RptUserService rptUserService;

    public RptUserController(RptUserService rptUserService){
        this.rptUserService = rptUserService;
    }

    /**
     * 用户列表
     * @param rptUserDTO
     * @return DefaultCommonResult<PageResultVO<RptUserVO>>
     */
    @PostMapping("/list")
    public DefaultCommonResult<PageResultVO<RptUserVO>> listByPage(@RequestBody  @Validated RptUserDTO rptUserDTO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,rptUserService.listByPage(rptUserDTO));
    }

    /**
     * 菜单权限
     * @return DefaultCommonResult<Boolean>
     */
    @GetMapping("/menu/permissions")
    public DefaultCommonResult<Boolean> menuPermissions(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient){
       return DefaultCommonResult.success(ResultCodeEnum.OK,rptUserService.menuPermissions(authorizedClient));
    }

    /**
     * 按钮权限
     * @return DefaultCommonResult<Boolean>
     */
    @GetMapping("/button/permissions")
    public DefaultCommonResult<Boolean> buttonPermissions(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient){
        return DefaultCommonResult.success(ResultCodeEnum.OK,rptUserService.buttonPermissions(authorizedClient));
    }
    /**
     * 查询用户信息
     *
     */
    @GetMapping("/info")
    public DefaultCommonResult<OidcUserInfo> userInfo(HttpServletRequest request) {
        SecurityContext securityContext =
                (SecurityContext) request.getSession().getAttribute(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
        OidcUser principal = (DefaultOidcUser) securityContext.getAuthentication().getPrincipal();
        return  DefaultCommonResult.success(ResultCodeEnum.OK,principal.getUserInfo());

    }

}
