package com.qk.dm.dataservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDasApiLimitInfo is a Querydsl query type for DasApiLimitInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDasApiLimitInfo extends EntityPathBase<DasApiLimitInfo> {

    private static final long serialVersionUID = 785407643L;

    public static final QDasApiLimitInfo dasApiLimitInfo = new QDasApiLimitInfo("dasApiLimitInfo");

    public final NumberPath<Integer> apiLimitCount = createNumber("apiLimitCount", Integer.class);

    public final NumberPath<Integer> appLimitCount = createNumber("appLimitCount", Integer.class);

    public final StringPath createUserid = createString("createUserid");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath limitName = createString("limitName");

    public final NumberPath<Integer> limitTime = createNumber("limitTime", Integer.class);

    public final StringPath limitTimeUnit = createString("limitTimeUnit");

    public final StringPath updateUserid = createString("updateUserid");

    public final NumberPath<Integer> userLimitCount = createNumber("userLimitCount", Integer.class);

    public QDasApiLimitInfo(String variable) {
        super(DasApiLimitInfo.class, forVariable(variable));
    }

    public QDasApiLimitInfo(Path<? extends DasApiLimitInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDasApiLimitInfo(PathMetadata metadata) {
        super(DasApiLimitInfo.class, metadata);
    }

}

