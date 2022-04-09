package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelDimTable is a Querydsl query type for ModelDimTable
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelDimTable extends EntityPathBase<ModelDimTable> {

    private static final long serialVersionUID = -150279215L;

    public static final QModelDimTable modelDimTable = new QModelDimTable("modelDimTable");

    public final StringPath connectionType = createString("connectionType");

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

    public final NumberPath<Long> modelDimId = createNumber("modelDimId", Long.class);

    public final NumberPath<Long> modelId = createNumber("modelId", Long.class);

    public final StringPath responsibleBy = createString("responsibleBy");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> synchronizationStatus = createNumber("synchronizationStatus", Integer.class);

    public final NumberPath<Long> themeId = createNumber("themeId", Long.class);

    public final StringPath themeName = createString("themeName");

    public QModelDimTable(String variable) {
        super(ModelDimTable.class, forVariable(variable));
    }

    public QModelDimTable(Path<? extends ModelDimTable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelDimTable(PathMetadata metadata) {
        super(ModelDimTable.class, metadata);
    }

}

