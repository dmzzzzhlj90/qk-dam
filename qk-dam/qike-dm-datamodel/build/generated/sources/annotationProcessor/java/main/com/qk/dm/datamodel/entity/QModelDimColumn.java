package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelDimColumn is a Querydsl query type for ModelDimColumn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelDimColumn extends EntityPathBase<ModelDimColumn> {

    private static final long serialVersionUID = -837147725L;

    public static final QModelDimColumn modelDimColumn = new QModelDimColumn("modelDimColumn");

    public final StringPath columnName = createString("columnName");

    public final StringPath columnType = createString("columnType");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> dimId = createNumber("dimId", Long.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itsNull = createString("itsNull");

    public final StringPath itsPartition = createString("itsPartition");

    public final StringPath itsPrimaryKey = createString("itsPrimaryKey");

    public final NumberPath<Long> standardsId = createNumber("standardsId", Long.class);

    public final StringPath standardsName = createString("standardsName");

    public QModelDimColumn(String variable) {
        super(ModelDimColumn.class, forVariable(variable));
    }

    public QModelDimColumn(Path<? extends ModelDimColumn> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelDimColumn(PathMetadata metadata) {
        super(ModelDimColumn.class, metadata);
    }

}

