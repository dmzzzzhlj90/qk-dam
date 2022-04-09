package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelSql is a Querydsl query type for ModelSql
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelSql extends EntityPathBase<ModelSql> {

    private static final long serialVersionUID = 909233987L;

    public static final QModelSql modelSql = new QModelSql("modelSql");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath sqlSentence = createString("sqlSentence");

    public final NumberPath<Long> tableId = createNumber("tableId", Long.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QModelSql(String variable) {
        super(ModelSql.class, forVariable(variable));
    }

    public QModelSql(Path<? extends ModelSql> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelSql(PathMetadata metadata) {
        super(ModelSql.class, metadata);
    }

}

