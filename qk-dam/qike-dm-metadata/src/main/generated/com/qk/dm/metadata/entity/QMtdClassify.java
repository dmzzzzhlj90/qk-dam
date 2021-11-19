package com.qk.dm.metadata.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMtdClassify is a Querydsl query type for MtdClassify
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMtdClassify extends EntityPathBase<MtdClassify> {

    private static final long serialVersionUID = 1484620279L;

    public static final QMtdClassify mtdClassify = new QMtdClassify("mtdClassify");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> synchStatus = createNumber("synchStatus", Integer.class);

    public QMtdClassify(String variable) {
        super(MtdClassify.class, forVariable(variable));
    }

    public QMtdClassify(Path<? extends MtdClassify> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMtdClassify(PathMetadata metadata) {
        super(MtdClassify.class, metadata);
    }

}

