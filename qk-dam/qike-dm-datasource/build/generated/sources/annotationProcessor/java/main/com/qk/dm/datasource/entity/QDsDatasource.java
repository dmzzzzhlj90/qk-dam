package com.qk.dm.datasource.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsDatasource is a Querydsl query type for DsDatasource
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDsDatasource extends EntityPathBase<DsDatasource> {

    private static final long serialVersionUID = -1568045356L;

    public static final QDsDatasource dsDatasource = new QDsDatasource("dsDatasource");

    public final StringPath createUserid = createString("createUserid");

    public final StringPath dataSourceName = createString("dataSourceName");

    public final StringPath dataSourceValues = createString("dataSourceValues");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath deployPlace = createString("deployPlace");

    public final StringPath dicId = createString("dicId");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final StringPath homeSystem = createString("homeSystem");

    public final StringPath id = createString("id");

    public final StringPath linkType = createString("linkType");

    public final StringPath purpose = createString("purpose");

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath tagIds = createString("tagIds");

    public final StringPath tagNames = createString("tagNames");

    public final StringPath tenantIdentification = createString("tenantIdentification");

    public final StringPath updateUserid = createString("updateUserid");

    public QDsDatasource(String variable) {
        super(DsDatasource.class, forVariable(variable));
    }

    public QDsDatasource(Path<? extends DsDatasource> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsDatasource(PathMetadata metadata) {
        super(DsDatasource.class, metadata);
    }

}

