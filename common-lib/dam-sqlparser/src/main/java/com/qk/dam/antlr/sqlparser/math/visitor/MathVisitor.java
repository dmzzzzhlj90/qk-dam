package com.qk.dam.antlr.sqlparser.math.visitor;


import com.qk.dam.antlr.sqlparser.math.parser.MathParserBaseVisitor;
import com.qk.dam.antlr.sqlparser.math.parser.MathParserParser;

import java.util.HashMap;
import java.util.Map;
public class MathVisitor extends MathParserBaseVisitor<Integer> {

    Map<String, Integer> memory = new HashMap<String, Integer>();

    @Override
    public Integer visitPrintExpr(MathParserParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr());
        System.out.println(value);
        return 0;
    }

    @Override
    public Integer visitAssign(MathParserParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        int value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitBlank(MathParserParser.BlankContext ctx) {
        return super.visitBlank(ctx);
    }

    @Override
    public Integer visitParens(MathParserParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Integer visitMulDiv(MathParserParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == MathParserParser.MUL) return left * right;
        return left / right;
    }

    @Override
    public Integer visitAddSub(MathParserParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == MathParserParser.ADD) return left + right;
        return left - right;
    }

    @Override
    public Integer visitId(MathParserParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        System.out.println(memory);
        return 0;
    }

    @Override
    public Integer visitInt(MathParserParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }
}
