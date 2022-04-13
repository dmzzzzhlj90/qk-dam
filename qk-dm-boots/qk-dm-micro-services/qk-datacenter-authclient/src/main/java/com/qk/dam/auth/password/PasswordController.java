package com.qk.dam.auth.password;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.NotAuthorizedException;

/**
 * @author shenpj
 * @date 2022/4/12 15:51
 * @since 1.0.0
 */
@RestController
public class PasswordController {
    private final KeyCloakConfig keyCloakConfig;

    public PasswordController(KeyCloakConfig keyCloakConfig) {
        this.keyCloakConfig = keyCloakConfig;
    }

    /**
     * 密码登陆
     *
     * @param loginInfoVO
     * @return DefaultCommonResult<TokenInfoVO>
     */
    @PostMapping("/auth/password/login")
    public DefaultCommonResult<TokenInfoVO> password_login(@RequestBody LoginInfoVO loginInfoVO) {
        try {
            return DefaultCommonResult.success(ResultCodeEnum.OK, keyCloakConfig.getToken(loginInfoVO.username, loginInfoVO.password));
        } catch (NotAuthorizedException n) {
            return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST, "账号或密码错误");
        } catch (Exception e) {
            return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST, "内部错误，请联系管理员");
        }

    }
}
