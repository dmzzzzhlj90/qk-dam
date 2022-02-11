package com.qk.dm.reptile.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.params.dto.RptUserDTO;
import com.qk.dm.reptile.params.vo.RptUserVO;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;


public interface RptUserService {
    PageResultVO<RptUserVO> listByPage(RptUserDTO rptUserDTO);

    Boolean menuPermissions(OAuth2AuthorizedClient authorizedClient);

    Boolean buttonPermissions(OAuth2AuthorizedClient authorizedClient);
}
