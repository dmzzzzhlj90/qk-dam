package com.qk.dm.reptile.service.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.google.common.collect.Maps;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.client.ClientUserInfo;
import com.qk.dm.reptile.client.DataSource;
import com.qk.dm.reptile.constant.RptUserConstant;
import com.qk.dm.reptile.params.dto.RptUserDTO;
import com.qk.dm.reptile.params.vo.RptUserVO;
import com.qk.dm.reptile.service.RptUserService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户信息
 * @author wangzp
 * @date 2022/02/05 10:13
 * @since 1.0.0
 */
@Service
public class RptUserServiceImpl implements RptUserService {

    private  Db DB ;
    private final JwtDecoder jwtDecoder;
    private final DataSource dataSource;

    public RptUserServiceImpl(JwtDecoder jwtDecoder,DataSource dataSource){
        this.jwtDecoder = jwtDecoder;
        this.dataSource = dataSource;
    }

    @Override
    public PageResultVO<RptUserVO> listByPage(RptUserDTO rptUserDTO) {
        Map<String, Object> map = getEntity(rptUserDTO);
        List<Entity> list = (List<Entity>) map.get("list");
        List<RptUserVO> userList =  list.stream().map(e ->
                RptUserVO.builder().id(e.getStr(RptUserConstant.ID)).userName( e.getStr(RptUserConstant.USER_NAME)).build()
        ).collect(Collectors.toList());
        return new PageResultVO<>(
                (long) map.get("total"),
                rptUserDTO.getPagination().getPage(),
                rptUserDTO.getPagination().getSize(),
                userList);
    }

    @Override
    public Boolean menuPermissions(OAuth2AuthorizedClient authorizedClient) {
        return ClientUserInfo.menuPermissions(jwtDecoder,authorizedClient);

    }

    @Override
    public Boolean buttonPermissions(OAuth2AuthorizedClient authorizedClient) {
        return ClientUserInfo.buttonPermissions(jwtDecoder,authorizedClient);
    }


    private Map<String, Object>  getEntity(RptUserDTO rptUserDTO){
        Map<String, Object> result = Maps.newHashMap();
        try {
            if(StringUtils.isNotBlank(rptUserDTO.getUserName())){
                List<Entity> list = DB.query(RptUserConstant.SELECT+RptUserConstant.WHERE, RptUserConstant.CLIENT,
                        "%" + rptUserDTO.getUserName() + "%", (rptUserDTO.getPagination().getPage() - 1)
                                * rptUserDTO.getPagination().getSize(), rptUserDTO.getPagination().getSize());
                long count = DB.count(RptUserConstant.SELECT+RptUserConstant.COUNT_WHERE, RptUserConstant.CLIENT,
                        "%" + rptUserDTO.getUserName() + "%");
                result.put("list", list);
                result.put("total", count);
            }else {
                List<Entity> list = DB.query(RptUserConstant.SELECT+RptUserConstant.PAGE, RptUserConstant.CLIENT, (rptUserDTO.getPagination().getPage() - 1)
                        * rptUserDTO.getPagination().getSize(), rptUserDTO.getPagination().getSize());
                long count = DB.count(RptUserConstant.SELECT, RptUserConstant.CLIENT);
                result.put("list", list);
                result.put("total", count);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    @PostConstruct
    private void getDb(){
       DB =   Db.use(
               new SimpleDataSource(
                       dataSource.getUrl(), dataSource.getUser(),
                       dataSource.getPassword(), dataSource.getDriver()));
    }

}
