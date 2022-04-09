package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelPhysicalTable is a Querydsl query type for ModelPhysicalTable
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelPhysicalTable extends EntityPathBase<ModelPhysicalTable> {

    private static final long serialVersionUID = -777790356L;

    public static final QModelPhysicalTable modelPhysicalTable = new QModelPhysicalTable("modelPhysicalTable");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath dataBaseName = createString("dataBaseName");

    public final StringPath dataConnection = createString("dataConnection");

    public final StringPath dataFormat = createString("dataFormat");

    public final NumberPath<Integer> dataSourceId = createNumber("dataSourceId", Integer.class);

    public final StringPath dataSourceName = createString("dataSourceName");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final StringPath hftsRoute = createString("hftsRoute");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> modelId = createNumber("modelId", Long.class);

    public final StringPath responsibleBy = createString("responsibleBy");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> syncStatus = createNumber("syncStatus", Integer.class);

    public final StringPath tableName = createString("tableName");

    public final StringPath tableType = createString("tableType");

    public final StringPath theme = createString("theme");

    public final StringPath themeId = createString("themeId");

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public QModelPhysicalTable(String variable) {
        super(ModelPhysicalTable.class, forVariable(variable));
    }

    public QModelPhysicalTable(Path<? extends ModelPhysicalTable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelPhysicalTable(PathMetadata metadata) {
        super(ModelPhysicalTable.class, metadata);
    }

}

