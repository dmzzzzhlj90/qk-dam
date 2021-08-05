package com.qk.dam.commons.http.result;

import java.io.Serializable;
import lombok.Data;

/**
 * @author daomingzhu
 * @since 1.0.0
 * @date 20210601 基础返回结果封装
 */
@Data
public abstract class BaseResult<T> implements Serializable {

  private static final long serialVersionUID = 7551352577404865782L;
  /** 返回码 */
  private String retCode;
  /** 返回说明 */
  private String retMsg;

  /** 提示 */
  private String tips;

  /** 返回数据 */
  private T data;
}
