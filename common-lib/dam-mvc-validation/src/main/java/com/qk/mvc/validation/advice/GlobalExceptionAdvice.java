package com.qk.mvc.validation.advice;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.http.result.BaseResult;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author daomingzhu
 * @date 2021/06/01
 * @since 1.0.0 全局异常逻辑处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

  /**
   * 统一的异常封装
   *
   * @param exception DmBizException
   * @return 返回消息
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public <T> BaseResult<T> sendErrorResponse(Exception exception) {
    log.error("后台服务异常: {}", getStackTrace(exception));
    return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST);
  }

  /**
   * 系统运行时异常
   *
   * @param exception RuntimeException
   * @return 返回消息
   */
  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public <T> BaseResult<T> sendErrorResponse(RuntimeException exception) {
    log.error("系统运行时异常: {}", getStackTrace(exception));
    return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST);
  }

  /**
   * 业务异常
   *
   * @param exception DmBizException
   * @return 返回消息
   */
  @ExceptionHandler(BizException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public <T> BaseResult<T> sendErrorResponse(BizException exception) {
    log.error("系统运行时异常: {}", getStackTrace(exception));
    return DefaultCommonResult.fail(ResultCodeEnum.BAD_REQUEST, exception.getLocalizedMessage());
  }

  /**
   * 单个参数校验（没有绑定对象）
   *
   * @param e ConstraintViolationException
   * @return 返回的信息
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public <T> BaseResult<T> otherValidException(ConstraintViolationException e) {
    log.error("参数校验异常: {}", getStackTrace(e));
    return DefaultCommonResult.fail(ResultCodeEnum.CONSTRAINT_VIOLATION_ERROR);
  }
  /**
   * 必填参数缺失
   *
   * @param e ServletRequestBindingException
   * @return 返回的信息
   */
  @ExceptionHandler(ServletRequestBindingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public <T> BaseResult<T> servletRequestBinding(ServletRequestBindingException e) {
    log.error("参数校验异常: {}", getStackTrace(e));
    return DefaultCommonResult.fail(ResultCodeEnum.SERVLET_REQUEST_BINDING_ERROR);
  }

  /**
   * 参数错误异常
   *
   * @param e 异常参数
   * @return 返回的信息
   */
  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public <T> BaseResult<T> handleException(Exception e) {
    var errorMsg = new StringBuilder(64);
    if (e instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
      var result = validException.getBindingResult();
      if (result.hasErrors()) {
        List<ObjectError> errors = result.getAllErrors();
        errors.forEach(
            p -> {
              FieldError fieldError = (FieldError) p;
              errorMsg.append(fieldError.getDefaultMessage()).append(",");
            });
      }
    } else if (e instanceof BindException) {
      var bindException = (BindException) e;
      if (bindException.hasErrors()) {
        bindException
            .getAllErrors()
            .forEach(
                objectError -> {
                  if (objectError.getCodes() != null) {
                    List<String> codeMsg =
                        Arrays.stream(objectError.getCodes())
                            .findFirst()
                            .map(
                                code ->
                                    code.replace("." + objectError.getCode(), "")
                                        + ":"
                                        + objectError.getDefaultMessage())
                            .stream()
                            .sorted()
                            .collect(Collectors.toList());
                    errorMsg.append(codeMsg);
                  }
                });
      }
    }
    log.error("参数校验异常: {}", errorMsg);
    log.error("异常详情: {}", getStackTrace(e));
    return DefaultCommonResult.fail(
        ResultCodeEnum.SERVLET_REQUEST_BINDING_ERROR, String.valueOf(errorMsg));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public <T> BaseResult<T> sendErrorResponse(HttpRequestMethodNotSupportedException exception) {
    log.error("http方法不允许: {}", getStackTrace(exception));
    return DefaultCommonResult.fail(ResultCodeEnum.HTTP_METHOD_NOT_ALLOW_ERROR);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  public <T> BaseResult<T> sendErrorResponse(HttpMediaTypeNotSupportedException exception) {
    return DefaultCommonResult.fail(ResultCodeEnum.HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR);
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public <T> BaseResult<T> sendErrorResponse(MaxUploadSizeExceededException exception) {
    return DefaultCommonResult.fail(ResultCodeEnum.MAX_UPLOAD_SIZE_EXCEEDED);
  }

  private static String getStackTrace(Throwable throwable) {
    var sw = new StringWriter();
    var pw = new PrintWriter(sw, true);
    throwable.printStackTrace(pw);
    return sw.getBuffer().toString();
  }
}
