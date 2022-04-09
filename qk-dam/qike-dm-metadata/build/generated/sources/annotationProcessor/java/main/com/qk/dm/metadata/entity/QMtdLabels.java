package com.qk.dm.metadata.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMtdLabels is a Querydsl query type for MtdLabels
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMtdLabels extends EntityPathBase<MtdLabels> {

    private static final long serialVersionUID = -1650374606L;

    public static final QMtdLabels mtdLabels = new QMtdLabels("mtdLabels");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> synchStatus = createNumber("synchStatus", Integer.class);

    public QMtdLabels(String variable) {
        super(MtdLabels.class, forVariable(variable));
    }

    public QMtdLabels(Path<? extends MtdLabels> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMtdLabels(PathMetadata metadata) {
        super(MtdLabels.class, metadata);
    }

}

