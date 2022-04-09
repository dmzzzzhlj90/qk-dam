package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelFactColumn is a Querydsl query type for ModelFactColumn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelFactColumn extends EntityPathBase<ModelFactColumn> {

    private static final long serialVersionUID = 32531917L;

    public static final QModelFactColumn modelFactColumn = new QModelFactColumn("modelFactColumn");

    public final StringPath columnName = createString("columnName");

    public final StringPath columnType = createString("columnType");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> factId = createNumber("factId", Long.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> itsNull = createNumber("itsNull", Integer.class);

    public final NumberPath<Integer> itsPartition = createNumber("itsPartition", Integer.class);

    public final NumberPath<Integer> itsPrimaryKey = createNumber("itsPrimaryKey", Integer.class);

    public final NumberPath<Long> standardsId = createNumber("standardsId", Long.class);

    public final StringPath standardsName = createString("standardsName");

    public QModelFactColumn(String variable) {
        super(ModelFactColumn.class, forVariable(variable));
    }

    public QModelFactColumn(Path<? extends ModelFactColumn> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelFactColumn(PathMetadata metadata) {
        super(ModelFactColumn.class, metadata);
    }

}

