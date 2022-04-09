package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelPhysicalRelation is a Querydsl query type for ModelPhysicalRelation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelPhysicalRelation extends EntityPathBase<ModelPhysicalRelation> {

    private static final long serialVersionUID = 537144478L;

    public static final QModelPhysicalRelation modelPhysicalRelation = new QModelPhysicalRelation("modelPhysicalRelation");

    public final StringPath childConnectionWay = createString("childConnectionWay");

    public final StringPath childTableColumn = createString("childTableColumn");

    public final StringPath childTableName = createString("childTableName");

    public final StringPath columnName = createString("columnName");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath fatherConnectionWay = createString("fatherConnectionWay");

    public final StringPath fatherTableColumn = createString("fatherTableColumn");

    public final StringPath fatherTableName = createString("fatherTableName");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> tableId = createNumber("tableId", Long.class);

    public QModelPhysicalRelation(String variable) {
        super(ModelPhysicalRelation.class, forVariable(variable));
    }

    public QModelPhysicalRelation(Path<? extends ModelPhysicalRelation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelPhysicalRelation(PathMetadata metadata) {
        super(ModelPhysicalRelation.class, metadata);
    }

}

