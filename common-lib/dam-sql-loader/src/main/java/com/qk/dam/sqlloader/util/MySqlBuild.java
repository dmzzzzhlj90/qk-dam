package com.qk.dam.sqlloader.util;

import com.qk.dam.antlr.sqlparser.mysql.MySqlContext;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlLexer;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParser;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParserBaseListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

public class MySqlBuild {
    public static List<String> buildSql(String sql){

        MySqlLexer lexer = new MySqlLexer(CharStreams.fromString(sql));

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        MySqlParser parser = new MySqlParser(tokenStream);

        MySqlParser.RootContext rootContext = parser.root();

        MySqlContext listenerSqlContext = new MySqlContext();
        ParseTreeWalker walker = new ParseTreeWalker();

        final List<String> listSql = new ArrayList<>();
        walker.walk(new MySqlParserBaseListener(){
            @Override
            public void enterInsertStatement(MySqlParser.InsertStatementContext ctx) {
                listSql.add(ctx.getText());
                super.enterInsertStatement(ctx);
            }

        }, rootContext);
        return listSql;
    }
}
