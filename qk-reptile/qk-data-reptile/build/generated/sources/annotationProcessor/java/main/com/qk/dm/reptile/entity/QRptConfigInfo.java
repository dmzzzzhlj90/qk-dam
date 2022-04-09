package com.qk.dm.reptile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRptConfigInfo is a Querydsl query type for RptConfigInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRptConfigInfo extends EntityPathBase<RptConfigInfo> {

    private static final long serialVersionUID = -1320999172L;

    public static final QRptConfigInfo rptConfigInfo = new QRptConfigInfo("rptConfigInfo");

    public final NumberPath<Long> baseInfoId = createNumber("baseInfoId", Long.class);

    public final StringPath cookies = createString("cookies");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath createUsername = createString("createUsername");

    public final StringPath description = createString("description");

    public final StringPath dimensionCode = createString("dimensionCode");

    public final NumberPath<Long> dimensionId = createNumber("dimensionId", Long.class);

    public final StringPath dimensionName = createString("dimensionName");

    public final StringPath formData = createString("formData");

    public final StringPath formUrlencoded = createString("formUrlencoded");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final StringPath headers = createString("headers");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final StringPath raw = createString("raw");

    public final StringPath requestType = createString("requestType");

    public final StringPath requestUrl = createString("requestUrl");

    public final NumberPath<Integer> startoverIp = createNumber("startoverIp", Integer.class);

    public final NumberPath<Integer> startoverJs = createNumber("startoverJs", Integer.class);

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public final StringPath updateUsername = createString("updateUsername");

    public QRptConfigInfo(String variable) {
        super(RptConfigInfo.class, forVariable(variable));
    }

    public QRptConfigInfo(Path<? extends RptConfigInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRptConfigInfo(PathMetadata metadata) {
        super(RptConfigInfo.class, metadata);
    }

}

