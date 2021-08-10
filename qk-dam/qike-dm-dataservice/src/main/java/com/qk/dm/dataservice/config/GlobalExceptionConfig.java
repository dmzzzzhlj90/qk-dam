package com.qk.dm.dataservice.config;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/** @author shenpengjie */
// @RestControllerAdvice
// @Order(-1)
// @ResponseBody
public class GlobalExceptionConfig {

  @ExceptionHandler(value = BindException.class)
  public DefaultCommonResult bindExceptionErrorHandler(BindException e) {
    BindingResult result = e.getBindingResult();
    StringBuffer errorString = new StringBuffer();
    if (result.hasErrors()) {
      List<FieldError> fieldErrors = result.getFieldErrors();
      int i = 1;
      fieldErrors.forEach(
          error -> {
            errorString.append(error.getDefaultMessage());
            if (i < fieldErrors.size()) {
              errorString.append(",");
            }
          });
    }
    return DefaultCommonResult.fail(ResultCodeEnum.SERVLET_REQUEST_BINDING_ERROR);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public DefaultCommonResult missingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    return DefaultCommonResult.fail(ResultCodeEnum.PARAM_IS_INVALID);
  }
}
