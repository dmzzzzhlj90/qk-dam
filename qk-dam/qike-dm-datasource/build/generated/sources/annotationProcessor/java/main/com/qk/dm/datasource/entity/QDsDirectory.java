package com.qk.dm.datasource.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsDirectory is a Querydsl query type for DsDirectory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDsDirectory extends EntityPathBase<DsDirectory> {

    private static final long serialVersionUID = -113935874L;

    public static final QDsDirectory dsDirectory = new QDsDirectory("dsDirectory");

    public final StringPath area = createString("area");

    public final StringPath busiDepartment = createString("busiDepartment");

    public final NumberPath<Integer> createUserid = createNumber("createUserid", Integer.class);

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath deployPlace = createString("deployPlace");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final StringPath id = createString("id");

    public final NumberPath<Integer> importance = createNumber("importance", Integer.class);

    public final StringPath itDepartment = createString("itDepartment");

    public final StringPath leader = createString("leader");

    public final StringPath sysDesc = createString("sysDesc");

    public final StringPath sysName = createString("sysName");

    public final StringPath sysShortName = createString("sysShortName");

    public final StringPath tagIds = createString("tagIds");

    public final StringPath tagNames = createString("tagNames");

    public final NumberPath<Integer> updateUserid = createNumber("updateUserid", Integer.class);

    public final StringPath versionConsumer = createString("versionConsumer");

    public QDsDirectory(String variable) {
        super(DsDirectory.class, forVariable(variable));
    }

    public QDsDirectory(Path<? extends DsDirectory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsDirectory(PathMetadata metadata) {
        super(DsDirectory.class, metadata);
    }

}

