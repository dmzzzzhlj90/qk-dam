package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelDim is a Querydsl query type for ModelDim
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelDim extends EntityPathBase<ModelDim> {

    private static final long serialVersionUID = 909219325L;

    public static final QModelDim modelDim = new QModelDim("modelDim");

    public final StringPath connectionType = createString("connectionType");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath databaseName = createString("databaseName");

    public final StringPath dataConnection = createString("dataConnection");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath dimCode = createString("dimCode");

    public final StringPath dimName = createString("dimName");

    public final StringPath dimType = createString("dimType");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> modelId = createNumber("modelId", Long.class);

    public final StringPath responsibleBy = createString("responsibleBy");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Long> themeId = createNumber("themeId", Long.class);

    public final StringPath themeName = createString("themeName");

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public QModelDim(String variable) {
        super(ModelDim.class, forVariable(variable));
    }

    public QModelDim(Path<? extends ModelDim> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelDim(PathMetadata metadata) {
        super(ModelDim.class, metadata);
    }

}

