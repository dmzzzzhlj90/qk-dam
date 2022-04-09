package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelPhysicalColumn is a Querydsl query type for ModelPhysicalColumn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelPhysicalColumn extends EntityPathBase<ModelPhysicalColumn> {

    private static final long serialVersionUID = 1184843384L;

    public static final QModelPhysicalColumn modelPhysicalColumn = new QModelPhysicalColumn("modelPhysicalColumn");

    public final StringPath columnName = createString("columnName");

    public final StringPath columnType = createString("columnType");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itsNull = createString("itsNull");

    public final StringPath itsPartition = createString("itsPartition");

    public final StringPath itsPrimaryKey = createString("itsPrimaryKey");

    public final StringPath standardsCode = createString("standardsCode");

    public final NumberPath<Long> standardsId = createNumber("standardsId", Long.class);

    public final StringPath standardsName = createString("standardsName");

    public final NumberPath<Long> tableId = createNumber("tableId", Long.class);

    public QModelPhysicalColumn(String variable) {
        super(ModelPhysicalColumn.class, forVariable(variable));
    }

    public QModelPhysicalColumn(Path<? extends ModelPhysicalColumn> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelPhysicalColumn(PathMetadata metadata) {
        super(ModelPhysicalColumn.class, metadata);
    }

}

