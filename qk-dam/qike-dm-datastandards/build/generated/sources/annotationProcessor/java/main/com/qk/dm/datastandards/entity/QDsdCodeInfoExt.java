package com.qk.dm.datastandards.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsdCodeInfoExt is a Querydsl query type for DsdCodeInfoExt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDsdCodeInfoExt extends EntityPathBase<DsdCodeInfoExt> {

    private static final long serialVersionUID = 958861372L;

    public static final QDsdCodeInfoExt dsdCodeInfoExt = new QDsdCodeInfoExt("dsdCodeInfoExt");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final NumberPath<Long> dsdCodeInfoId = createNumber("dsdCodeInfoId", Long.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath searchCode = createString("searchCode");

    public final StringPath searchValue = createString("searchValue");

    public final StringPath tableConfExtValues = createString("tableConfExtValues");

    public QDsdCodeInfoExt(String variable) {
        super(DsdCodeInfoExt.class, forVariable(variable));
    }

    public QDsdCodeInfoExt(Path<? extends DsdCodeInfoExt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsdCodeInfoExt(PathMetadata metadata) {
        super(DsdCodeInfoExt.class, metadata);
    }

}

