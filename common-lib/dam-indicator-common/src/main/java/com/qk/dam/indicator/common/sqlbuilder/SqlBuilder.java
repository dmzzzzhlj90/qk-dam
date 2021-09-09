package com.qk.dam.indicator.common.sqlbuilder;

import tech.ibit.sqlbuilder.StringSql;

public class SqlBuilder {

    private SqlBuilder(){}

    private static class Sql{
        private static final StringSql builder = new StringSql();
    }

    public static StringSql builder(){
        return Sql.builder;
    }
}
