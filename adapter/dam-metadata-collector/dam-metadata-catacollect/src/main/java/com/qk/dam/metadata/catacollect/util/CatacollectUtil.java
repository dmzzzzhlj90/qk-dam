package com.qk.dam.metadata.catacollect.util;

import java.util.List;

/**
 * @author zys
 * @date 2022/4/14 20:04
 * @since 1.0.0
 */
public class CatacollectUtil {

  public static String convertListToString(List<String> lists) {
    StringBuffer sb = new StringBuffer();
    if(lists !=null){
      for (int i=0;i<lists.size();i++) {
        if(i==0){
          sb.append("'").append(lists.get(i)).append("'");
        }else{
          sb.append(",").append("'").append(lists.get(i)).append("'");
        }
      }
    }
    return sb.toString();
  }
}