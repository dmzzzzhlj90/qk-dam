package com.qk.dm.authority.util;

import com.qk.dm.authority.vo.user.AtyUserParamVO;

/**
 * @author zys
 * @date 2022/2/23 18:12
 * @since 1.0.0
 */
public class Util {
  public static Integer dealPage(AtyUserParamVO atyUserParamVO) {
    return  (atyUserParamVO.getPagination().getPage()-1)* atyUserParamVO.getPagination().getSize();
  }
}