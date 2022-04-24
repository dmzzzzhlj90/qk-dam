package com.qk.dam.sqlparser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.validation.Validation;
import net.sf.jsqlparser.util.validation.ValidationError;
import net.sf.jsqlparser.util.validation.feature.DatabaseType;

import java.util.*;
import java.util.stream.Collectors;

public class TestJava {
  public static void main(String[] args) throws JsonProcessingException {
    // result ===> INSERT INTO accc (a, b) VALUES (1, 2)
    Insert insert = new Insert();
    insert
        .withColumns(List.of(new Column("a"), new Column("b")))
        .withTable(new Table("accc"))
        .withUseValues(true)
        .withItemsList(new ExpressionList(new LongValue(1L), new LongValue(2L)));
    System.out.println(insert);

    // validation sql
    Validation validation =
        new Validation(
            Arrays.asList(DatabaseType.DATABASES), "SELECT * FROM tab1; SELECT * FROM tab2");
    validation.validate().stream().map(ValidationError::getErrors).forEach(System.out::println);

    // parse select get columns

    Select stmt = null;
    try {
      stmt =
          (Select)
              CCJSqlParserUtil.parse(
                  "SELECT t.id as 列1,t.name as 列2,q.entid as 列3,q.entname FROM tab1 t left join tab2 q on t.id =q.id where t.id=12 and t.name='zzd';");
    } catch (JSQLParserException e) {
      throw new RuntimeException("解析SQL语句失败！");
    }
    PlainSelect plainSelect = (PlainSelect) stmt.getSelectBody();

    Map<String, String> colAndAliasMap =
        plainSelect.getSelectItems().stream()
            .filter(SelectExpressionItem.class::isInstance)
            .distinct()
            .collect(
                Collectors.toMap(
                    k -> ((Column) ((SelectExpressionItem) k).getExpression()).getColumnName(),
                    v ->
                        Objects.nonNull(((SelectExpressionItem) v).getAlias())
                            ? ((SelectExpressionItem) v).getAlias().getName()
                            : ""));

    System.out.println(colAndAliasMap);
    System.out.println(new ObjectMapper().writeValueAsString(plainSelect.getSelectItems()));
  }
}
