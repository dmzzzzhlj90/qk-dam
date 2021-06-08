// Generated from F:/seata/git-seata/seata-sqlparser-antlr/src/main/antlr4/com.alibaba.seata.mysql.sqlparser/mysql\MathParser.g4 by ANTLR 4.8
package com.qk.dam.antlr.sqlparser.math.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MathParserParser}.
 */
public interface MathParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MathParserParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(MathParserParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathParserParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(MathParserParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link MathParserParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpr(MathParserParser.PrintExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link MathParserParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpr(MathParserParser.PrintExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign}
	 * labeled alternative in {@link MathParserParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssign(MathParserParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link MathParserParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssign(MathParserParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blank}
	 * labeled alternative in {@link MathParserParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBlank(MathParserParser.BlankContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link MathParserParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBlank(MathParserParser.BlankContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(MathParserParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(MathParserParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(MathParserParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(MathParserParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(MathParserParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(MathParserParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterId(MathParserParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitId(MathParserParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(MathParserParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link MathParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(MathParserParser.IntContext ctx);
}