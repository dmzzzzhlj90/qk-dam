package com.qk.dm.reptile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRptBaseInfo is a Querydsl query type for RptBaseInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRptBaseInfo extends EntityPathBase<RptBaseInfo> {

    private static final long serialVersionUID = -343058933L;

    public static final QRptBaseInfo rptBaseInfo = new QRptBaseInfo("rptBaseInfo");

    public final StringPath areaCode = createString("areaCode");

    public final StringPath assignedPersonName = createString("assignedPersonName");

    public final StringPath cityCode = createString("cityCode");

    public final DateTimePath<java.util.Date> configDate = createDateTime("configDate", java.util.Date.class);

    public final NumberPath<Long> configId = createNumber("configId", Long.class);

    public final StringPath configName = createString("configName");

    public final BooleanPath configStatus = createBoolean("configStatus");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath createUsername = createString("createUsername");

    public final DateTimePath<java.util.Date> delDate = createDateTime("delDate", java.util.Date.class);

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final DateTimePath<java.util.Date> deliveryDate = createDateTime("deliveryDate", java.util.Date.class);

    public final StringPath delUserName = createString("delUserName");

    public final StringPath differentTypeMixed = createString("differentTypeMixed");

    public final DateTimePath<java.util.Date> distributionDate = createDateTime("distributionDate", java.util.Date.class);

    public final StringPath executor = createString("executor");

    public final NumberPath<Long> executorId = createNumber("executorId", Long.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtFunction = createDateTime("gmtFunction", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath infoReleaseLevel = createString("infoReleaseLevel");

    public final StringPath jobId = createString("jobId");

    public final StringPath listPageAddress = createString("listPageAddress");

    public final StringPath provinceCode = createString("provinceCode");

    public final StringPath regionCode = createString("regionCode");

    public final StringPath responsiblePersonName = createString("responsiblePersonName");

    public final StringPath runPeriod = createString("runPeriod");

    public final NumberPath<Integer> runStatus = createNumber("runStatus", Integer.class);

    public final StringPath secondSiteType = createString("secondSiteType");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath timeInterval = createString("timeInterval");

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public final StringPath updateUsername = createString("updateUsername");

    public final StringPath websiteName = createString("websiteName");

    public final StringPath websiteNameCorrection = createString("websiteNameCorrection");

    public final StringPath websiteUrl = createString("websiteUrl");

    public final StringPath websiteUrlCorrection = createString("websiteUrlCorrection");

    public QRptBaseInfo(String variable) {
        super(RptBaseInfo.class, forVariable(variable));
    }

    public QRptBaseInfo(Path<? extends RptBaseInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRptBaseInfo(PathMetadata metadata) {
        super(RptBaseInfo.class, metadata);
    }

}

