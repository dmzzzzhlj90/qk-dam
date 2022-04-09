package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelSummary is a Querydsl query type for ModelSummary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelSummary extends EntityPathBase<ModelSummary> {

    private static final long serialVersionUID = 628553371L;

    public static final QModelSummary modelSummary = new QModelSummary("modelSummary");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath databaseName = createString("databaseName");

    public final StringPath dataConnection = createString("dataConnection");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> dimId = createNumber("dimId", Long.class);

    public final StringPath dimName = createString("dimName");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> modelId = createNumber("modelId", Long.class);

    public final StringPath responsibleBy = createString("responsibleBy");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath tableName = createString("tableName");

    public final NumberPath<Long> themeId = createNumber("themeId", Long.class);

    public final StringPath themeName = createString("themeName");

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public QModelSummary(String variable) {
        super(ModelSummary.class, forVariable(variable));
    }

    public QModelSummary(Path<? extends ModelSummary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelSummary(PathMetadata metadata) {
        super(ModelSummary.class, metadata);
    }

}

