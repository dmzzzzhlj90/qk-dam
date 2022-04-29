package com.qk.dam.sqlparser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.validation.Validation;
import net.sf.jsqlparser.util.validation.ValidationError;
import net.sf.jsqlparser.util.validation.feature.DatabaseType;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TestJava {
  private static final Pattern PATTERN_PATH_VAR = Pattern.compile("([$#])\\{([^}])*}");
  private static final Pattern PATTERN_PATH_VAR_TAG = Pattern.compile("<.*([^(</>)])*</.*>");
  public static void main(String[] args) throws JsonProcessingException {
    System.out.println(PATTERN_PATH_VAR.matcher("/asdasd/#{asd}/ww/${cc}").replaceAll("1"));
    System.out.println(PATTERN_PATH_VAR_TAG.matcher("/asdasd/#{asd}/ww/${cc}").replaceAll("1"));
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

    String sqlContent = "SELECT t.id as 列1 FROM tab1 t left join tab2 q on t.id =q.id where t.id=${enum} and t.we in <foreach asda asd as>sada</foreach> and t.name='zzd';";

    sqlContent = PATTERN_PATH_VAR.matcher(sqlContent).replaceAll("1");
    sqlContent = PATTERN_PATH_VAR_TAG.matcher(sqlContent).replaceAll("1");

    System.out.println("sqlContent===>"+sqlContent);
    List<SelectItem> selectItems = JSqlParserUtil.selectBody(sqlContent);

    System.out.println(new ObjectMapper().writeValueAsString(selectItems));
  }
}
