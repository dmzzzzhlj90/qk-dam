package com.qk.dam.antlr.sqlparser.mysql.visit;

import com.qk.dam.antlr.sqlparser.mysql.MySqlContext;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParser;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParserBaseListener;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParserBaseVisitor;


public class StatementSqlVisitor extends MySqlParserBaseVisitor<MySqlContext> {

    private MySqlContext mySqlContext;

    public StatementSqlVisitor(MySqlContext mySqlContext) {
        this.mySqlContext = mySqlContext;
    }

    @Override
    public MySqlContext visitInsertStatement(MySqlParser.InsertStatementContext ctx) {
        return new InsertSpecificationSqlVisitor(this.mySqlContext).visitInsertStatement(ctx);
    }

    @Override
    public MySqlContext visitFullColumnNameExpressionAtom(MySqlParser.FullColumnNameExpressionAtomContext ctx) {
        return new QuerySpecificationSqlVisitor(this.mySqlContext).visitFullColumnNameExpressionAtom(ctx);
    }

    @Override
    public MySqlContext visitTableSources(MySqlParser.TableSourcesContext ctx) {
        return new QuerySpecificationSqlVisitor(this.mySqlContext).visitTableSources(ctx);
    }

    @Override
    public MySqlContext visitConstantExpressionAtom(MySqlParser.ConstantExpressionAtomContext ctx) {
        return new QuerySpecificationSqlVisitor(this.mySqlContext).visitConstantExpressionAtom(ctx);
    }

    @Override
    public MySqlContext visitSelectElements(MySqlParser.SelectElementsContext ctx) {
        return new QuerySpecificationSqlVisitor(this.mySqlContext).visitSelectElements(ctx);
    }

    @Override
    public MySqlContext visitBetweenPredicate(MySqlParser.BetweenPredicateContext ctx) {
        return new QuerySpecificationSqlVisitor(this.mySqlContext).visitBetweenPredicate(ctx);
    }

    @Override
    public MySqlContext visitPredicateExpression(MySqlParser.PredicateExpressionContext ctx) {
        return new QuerySpecificationSqlVisitor(this.mySqlContext).visitPredicateExpression(ctx);
    }

    @Override
    public MySqlContext visitFromClause(MySqlParser.FromClauseContext ctx) {
        return new QuerySpecificationSqlVisitor(this.mySqlContext).visitFromClause(ctx);
    }
}