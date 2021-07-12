package com.qk.dm.datastandards.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsdTerm is a Querydsl query type for DsdTerm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDsdTerm extends EntityPathBase<DsdTerm> {

    private static final long serialVersionUID = 1480582358L;

    public static final QDsdTerm dsdTerm = new QDsdTerm("dsdTerm");

    public final StringPath chineseName = createString("chineseName");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath englishName = createString("englishName");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath rootName = createString("rootName");

    public final StringPath shortEnglishName = createString("shortEnglishName");

    public final NumberPath<Integer> state = createNumber("state", Integer.class);

    public QDsdTerm(String variable) {
        super(DsdTerm.class, forVariable(variable));
    }

    public QDsdTerm(Path<? extends DsdTerm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsdTerm(PathMetadata metadata) {
        super(DsdTerm.class, metadata);
    }

}

