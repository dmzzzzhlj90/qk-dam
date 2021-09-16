package com.qk.dm.dataservice.utils;

/**
 * 字符串格式化工具类
 *
 * @author wjq
 * @date 20210901
 * @since 1.0.0
 */
public class StringFormatUtils {
  /**
   * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
   *
   * @param name 转换前的下划线大写方式命名的字符串
   * @return 转换后的驼峰式命名的字符串
   */
  public static String camelName(String name) {
    StringBuilder result = new StringBuilder();
    // 快速检查
    if (name == null || name.isEmpty()) {
      // 没必要转换
      return "";
    } else if (!name.contains("_")) {
      // 不含下划线，仅将首字母小写
      return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
    // 用下划线将原始字符串分割
    String camels[] = name.split("_");
    for (String camel : camels) {
      // 跳过原始字符串中开头、结尾的下换线或双重下划线
      if (camel.isEmpty()) {
        continue;
      }
      // 处理真正的驼峰片段
      if (result.length() == 0) {
        // 第一个驼峰片段，全部字母都小写
        result.append(camel.substring(0, 1).toUpperCase() + camel.substring(1).toLowerCase());
      } else {
        // 其他的驼峰片段，首字母大写
        result.append(camel.substring(0, 1).toUpperCase());
        result.append(camel.substring(1).toLowerCase());
      }
    }
    return result.toString();
  }

  public static void main(String[] args) {
    final String hello_world = camelName("company_batch_query");
    System.out.println(hello_world);
  }
}
