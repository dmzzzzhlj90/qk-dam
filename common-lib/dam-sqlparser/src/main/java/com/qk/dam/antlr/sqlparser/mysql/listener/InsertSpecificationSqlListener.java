package com.qk.dam.antlr.sqlparser.mysql.listener;

import com.qk.dam.antlr.sqlparser.mysql.MySqlContext;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParser;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParserBaseListener;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.stream.Collectors;

public class InsertSpecificationSqlListener extends MySqlParserBaseListener {
    private MySqlContext sqlQueryContext;
    @Override
    public void enterTableName(MySqlParser.TableNameContext ctx) {

        String text = ctx.getText();
        sqlQueryContext.setTableName(text);
        super.enterTableName(ctx);
    }
    public InsertSpecificationSqlListener(MySqlContext sqlQueryContext) {
        this.sqlQueryContext = sqlQueryContext;
    }

    @Override
    public void enterTableOptionInsertMethod(MySqlParser.TableOptionInsertMethodContext ctx) {
        super.enterTableOptionInsertMethod(ctx);
    }

    @Override
    public void enterInsertStatement(MySqlParser.InsertStatementContext ctx) {
        super.enterInsertStatement(ctx);
    }

    @Override
    public void enterInsertStatementValue(MySqlParser.InsertStatementValueContext ctx) {
        super.enterInsertStatementValue(ctx);
    }

    @Override public void enterExpressionsWithDefaults(MySqlParser.ExpressionsWithDefaultsContext ctx) {
        List<String> values = ctx.children.stream()
                .filter(passtree -> passtree instanceof MySqlParser.ExpressionOrDefaultContext)
                .map(ParseTree::getText)
                .collect(Collectors.toList());
        sqlQueryContext.addForInsertValColumnName(values);
    }
    @Override public void enterExpressionOrDefault(MySqlParser.ExpressionOrDefaultContext ctx) {
        super.enterExpressionOrDefault(ctx);
    }
    @Override
    public void exitTableOptionInsertMethod(MySqlParser.TableOptionInsertMethodContext ctx) {
        super.exitTableOptionInsertMethod(ctx);
    }


}
