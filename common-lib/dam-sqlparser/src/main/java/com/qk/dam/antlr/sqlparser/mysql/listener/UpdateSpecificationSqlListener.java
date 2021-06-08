package com.qk.dam.antlr.sqlparser.mysql.listener;

import com.qk.dam.antlr.sqlparser.mysql.MySqlContext;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParser;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParserBaseListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class UpdateSpecificationSqlListener extends MySqlParserBaseListener {


    private MySqlContext sqlQueryContext;

    public UpdateSpecificationSqlListener(MySqlContext sqlQueryContext) {
        this.sqlQueryContext = sqlQueryContext;
    }

    @Override
    public void enterTableName(MySqlParser.TableNameContext ctx) {

        String text = ctx.getText();
        sqlQueryContext.setTableName(text);
        super.enterTableName(ctx);
    }


    @Override
    public void enterConstantExpressionAtomForUpdate(MySqlParser.ConstantExpressionAtomForUpdateContext ctx) {
        sqlQueryContext.addUpdateWhereValColumnNames(ctx.getText());
        super.enterConstantExpressionAtomForUpdate(ctx);
    }

    @Override
    public void enterFullColumnNameExpressionAtomForUpdate(MySqlParser.FullColumnNameExpressionAtomForUpdateContext ctx) {
        sqlQueryContext.addUpdateWhereColumnNames(ctx.getText());
        super.enterFullColumnNameExpressionAtomForUpdate(ctx);
    }

    @Override
    public void enterSingleUpdateStatement(MySqlParser.SingleUpdateStatementContext ctx) {
        MySqlParser.ExpressionForUpdateContext expressionForUpdateContext = ctx.expressionForUpdate();
        sqlQueryContext.setWhereCondition(expressionForUpdateContext.getText());
        super.enterSingleUpdateStatement(ctx);
    }

    @Override
    public void enterUpdatedElement(MySqlParser.UpdatedElementContext ctx) {

        MySqlParser.ExpressionContext expression = ctx.expression();
        sqlQueryContext.addUpdateValues(expression.getText());

        MySqlParser.FullColumnNameContext fullColumnNameContext = ctx.fullColumnName();
        sqlQueryContext.addUpdateColumnNames(fullColumnNameContext.getText());

        super.enterUpdatedElement(ctx);
    }

}