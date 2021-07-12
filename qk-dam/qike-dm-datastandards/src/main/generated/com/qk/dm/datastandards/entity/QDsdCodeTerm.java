package com.qk.dm.datastandards.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsdCodeTerm is a Querydsl query type for DsdCodeTerm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDsdCodeTerm extends EntityPathBase<DsdCodeTerm> {

    private static final long serialVersionUID = -866974877L;

    public static final QDsdCodeTerm dsdCodeTerm = new QDsdCodeTerm("dsdCodeTerm");

    public final StringPath codeDirId = createString("codeDirId");

    public final StringPath codeId = createString("codeId");

    public final StringPath codeName = createString("codeName");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> termId = createNumber("termId", Integer.class);

    public QDsdCodeTerm(String variable) {
        super(DsdCodeTerm.class, forVariable(variable));
    }

    public QDsdCodeTerm(Path<? extends DsdCodeTerm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsdCodeTerm(PathMetadata metadata) {
        super(DsdCodeTerm.class, metadata);
    }

}

