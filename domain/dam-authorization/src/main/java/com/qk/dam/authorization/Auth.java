package com.qk.dam.authorization;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
  RestActionType actionType();

  BizResource bizType();
}
