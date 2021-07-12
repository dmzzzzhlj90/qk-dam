package com.qk.dm.datastandards.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsdCodeDir is a Querydsl query type for DsdCodeDir
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDsdCodeDir extends EntityPathBase<DsdCodeDir> {

    private static final long serialVersionUID = 1080396470L;

    public static final QDsdCodeDir dsdCodeDir = new QDsdCodeDir("dsdCodeDir");

    public final StringPath codeDirId = createString("codeDirId");

    public final StringPath codeDirName = createString("codeDirName");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath parentId = createString("parentId");

    public QDsdCodeDir(String variable) {
        super(DsdCodeDir.class, forVariable(variable));
    }

    public QDsdCodeDir(Path<? extends DsdCodeDir> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsdCodeDir(PathMetadata metadata) {
        super(DsdCodeDir.class, metadata);
    }

}

