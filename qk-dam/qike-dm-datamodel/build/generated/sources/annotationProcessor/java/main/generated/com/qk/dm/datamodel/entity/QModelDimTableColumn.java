package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelDimTableColumn is a Querydsl query type for ModelDimTableColumn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelDimTableColumn extends EntityPathBase<ModelDimTableColumn> {

    private static final long serialVersionUID = 143182471L;

    public static final QModelDimTableColumn modelDimTableColumn = new QModelDimTableColumn("modelDimTableColumn");

    public final StringPath columnName = createString("columnName");

    public final StringPath columnType = createString("columnType");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> dimTableId = createNumber("dimTableId", Long.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itsNull = createString("itsNull");

    public final StringPath itsPartition = createString("itsPartition");

    public final StringPath itsPrimaryKey = createString("itsPrimaryKey");

    public final NumberPath<Long> standardsId = createNumber("standardsId", Long.class);

    public final StringPath standardsName = createString("standardsName");

    public QModelDimTableColumn(String variable) {
        super(ModelDimTableColumn.class, forVariable(variable));
    }

    public QModelDimTableColumn(Path<? extends ModelDimTableColumn> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelDimTableColumn(PathMetadata metadata) {
        super(ModelDimTableColumn.class, metadata);
    }

}

