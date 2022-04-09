package com.qk.dm.indicator.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdcComposite is a Querydsl query type for IdcComposite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdcComposite extends EntityPathBase<IdcComposite> {

    private static final long serialVersionUID = 1257180013L;

    public static final QIdcComposite idcComposite = new QIdcComposite("idcComposite");

    public final StringPath compositeIndicatorCode = createString("compositeIndicatorCode");

    public final StringPath compositeIndicatorName = createString("compositeIndicatorName");

    public final StringPath dataType = createString("dataType");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath dimStatistics = createString("dimStatistics");

    public final StringPath expression = createString("expression");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> indicatorStatus = createNumber("indicatorStatus", Integer.class);

    public final StringPath sqlSentence = createString("sqlSentence");

    public final StringPath themeCode = createString("themeCode");

    public QIdcComposite(String variable) {
        super(IdcComposite.class, forVariable(variable));
    }

    public QIdcComposite(Path<? extends IdcComposite> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdcComposite(PathMetadata metadata) {
        super(IdcComposite.class, metadata);
    }

}

