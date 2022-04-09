package com.qk.dm.reptile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRptDimensionInfo is a Querydsl query type for RptDimensionInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRptDimensionInfo extends EntityPathBase<RptDimensionInfo> {

    private static final long serialVersionUID = -813543480L;

    public static final QRptDimensionInfo rptDimensionInfo = new QRptDimensionInfo("rptDimensionInfo");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath createUsername = createString("createUsername");

    public final StringPath description = createString("description");

    public final StringPath dimensionCode = createString("dimensionCode");

    public final StringPath dimensionName = createString("dimensionName");

    public final NumberPath<Long> fid = createNumber("fid", Long.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public final StringPath updateUsername = createString("updateUsername");

    public QRptDimensionInfo(String variable) {
        super(RptDimensionInfo.class, forVariable(variable));
    }

    public QRptDimensionInfo(Path<? extends RptDimensionInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRptDimensionInfo(PathMetadata metadata) {
        super(RptDimensionInfo.class, metadata);
    }

}

