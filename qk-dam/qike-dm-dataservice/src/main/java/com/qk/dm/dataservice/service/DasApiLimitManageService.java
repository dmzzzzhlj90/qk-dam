package com.qk.dm.dataservice.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.vo.DasApiGroupRouteVO;
import com.qk.dm.dataservice.vo.DasApiLimitBindInfoVO;
import com.qk.dm.dataservice.vo.DasApiLimitInfoVO;
import com.qk.dm.dataservice.vo.DasApiLimitManageParamsVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据服务_服务流控管理
 *
 * @author wjq
 * @date 2022/03/16
 * @since 1.0.0
 */
@Service
public interface DasApiLimitManageService {

    PageResultVO<DasApiLimitInfoVO> searchList(DasApiLimitManageParamsVO dasApiLimitManageParamsVO);

    void insert(DasApiLimitInfoVO dasApiLimitInfoVO);

    void update(DasApiLimitInfoVO dasApiLimitInfoVO);

    void deleteBulk(String ids);

    void bind(DasApiLimitBindInfoVO dasApiLimitBindInfoVO);

    List<DasApiGroupRouteVO> routes();

    Map<String, String> timeUnit();

}
