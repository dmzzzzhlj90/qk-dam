package com.qk.dm.datastandards.easyexcel.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import java.lang.reflect.Field;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

/**
 * 数据格式验证类
 *
 * @author wjq
 * @date 20210803
 */
public class EasyExcelValidateHelper {
  private EasyExcelValidateHelper() {}

  private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public static <T> String validateEntity(T obj) throws NoSuchFieldException {
    StringBuilder result = new StringBuilder();
    Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
    if (set != null && !set.isEmpty()) {
      for (ConstraintViolation<T> cv : set) {
        Field declaredField = obj.getClass().getDeclaredField(cv.getPropertyPath().toString());
        if (null != declaredField.getAnnotation(ExcelProperty.class)) {
          result.append(cv.getMessage()).append(";");
        }
      }
    }
    return result.toString();
  }
}
