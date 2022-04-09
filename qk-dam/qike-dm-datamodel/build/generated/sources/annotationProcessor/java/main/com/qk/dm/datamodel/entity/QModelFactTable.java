package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelFactTable is a Querydsl query type for ModelFactTable
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelFactTable extends EntityPathBase<ModelFactTable> {

    private static final long serialVersionUID = -122225033L;

    public static final QModelFactTable modelFactTable = new QModelFactTable("modelFactTable");

    public final NumberPath<Integer> connectionType = createNumber("connectionType", Integer.class);

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath databaseName = createString("databaseName");

    public final StringPath dataConnection = createString("dataConnection");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath factName = createString("factName");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> modelId = createNumber("modelId", Long.class);

    public final StringPath responsibleBy = createString("responsibleBy");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Long> themeId = createNumber("themeId", Long.class);

    public final StringPath themeName = createString("themeName");

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public QModelFactTable(String variable) {
        super(ModelFactTable.class, forVariable(variable));
    }

    public QModelFactTable(Path<? extends ModelFactTable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelFactTable(PathMetadata metadata) {
        super(ModelFactTable.class, metadata);
    }

}

