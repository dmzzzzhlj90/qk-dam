package com.qk.dam.sqlparser;

import com.qk.dam.commons.exception.SqlParserException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;

/**
 * 操作解析Sql 工具类
 *
 * @author zhudaoming
 */
public class JSqlParserUtil {
  /**
   * 查询select语句的内容-包括了列，列别名，前缀库表名等
   *
   * @param selectSql select 查询语句
   * @return List<SelectItem> select语句的内容
   */
  public static List<SelectItem> selectBody(String selectSql) {
    Statement stmt = null;
    try {
      stmt = CCJSqlParserUtil.parse(selectSql);
    } catch (JSQLParserException e) {
      e.printStackTrace();
      throw new SqlParserException("解析SQL语句失败！");
    }

    if (stmt instanceof Select) {
      SelectBody selectBody = ((Select) stmt).getSelectBody();
      List<SelectItem> selectItems = ((PlainSelect) selectBody).getSelectItems();
      if (selectItems.stream().anyMatch(AllTableColumns.class::isInstance)) {
        throw new SqlParserException("select 语句禁止使用\"*\"描述符匹配所有字段！");
      }
      return selectItems;
    } else {
      throw new SqlParserException("非select 语句解析！");
    }
  }
}
