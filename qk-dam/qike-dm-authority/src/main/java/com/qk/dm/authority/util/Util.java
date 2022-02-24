package com.qk.dm.authority.util;

import com.qk.dm.authority.vo.params.UserParamVO;

/**
 * @author zys
 * @date 2022/2/23 18:12
 * @since 1.0.0
 */
public class Util {
  public static Integer dealPage(UserParamVO userParamVO) {
    return  (userParamVO.getPagination().getPage()-1)*userParamVO.getPagination().getSize();
  }
}