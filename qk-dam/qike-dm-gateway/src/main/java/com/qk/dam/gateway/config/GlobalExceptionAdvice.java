package com.qk.dam.gateway.config;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * @author daomingzhu
 * @date 2021/06/01
 * @since 1.0.0 全局异常逻辑处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
  /**
   * jwt校验异常
   *
   * @param e ServletRequestBindingException
   * @return 返回的信息
   */
  @ExceptionHandler(JwtException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<DefaultCommonResult<Object>> jwtException(JwtException e) {
    return Mono.justOrEmpty(DefaultCommonResult.error(ResultCodeEnum.UN_AUTHORIZED));
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<DefaultCommonResult<Object>> runtimeException(RuntimeException e) {
    return Mono.justOrEmpty(DefaultCommonResult.error(ResultCodeEnum.UN_AUTHORIZED));
  }

  @ExceptionHandler(BadJwtException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<DefaultCommonResult<Object>> badJwtException(BadJwtException e) {
    return Mono.justOrEmpty(DefaultCommonResult.error(ResultCodeEnum.UN_AUTHORIZED));
  }

  private static String getStackTrace(Throwable throwable) {
    var sw = new StringWriter();
    var pw = new PrintWriter(sw, true);
    throwable.printStackTrace(pw);
    return sw.getBuffer().toString();
  }
}
