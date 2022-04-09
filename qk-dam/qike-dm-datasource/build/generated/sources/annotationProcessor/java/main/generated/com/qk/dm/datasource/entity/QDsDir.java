package com.qk.dm.datasource.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsDir is a Querydsl query type for DsDir
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDsDir extends EntityPathBase<DsDir> {

    private static final long serialVersionUID = 912466142L;

    public static final QDsDir dsDir = new QDsDir("dsDir");

    public final StringPath createUserid = createString("createUserid");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath dicName = createString("dicName");

    public final StringPath dsDirCode = createString("dsDirCode");

    public final StringPath dsSystemId = createString("dsSystemId");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final StringPath id = createString("id");

    public final StringPath parentId = createString("parentId");

    public final StringPath updateUserid = createString("updateUserid");

    public final StringPath versionConsumer = createString("versionConsumer");

    public QDsDir(String variable) {
        super(DsDir.class, forVariable(variable));
    }

    public QDsDir(Path<? extends DsDir> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsDir(PathMetadata metadata) {
        super(DsDir.class, metadata);
    }

}

