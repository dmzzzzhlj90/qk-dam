package com.qk.dm.indicator.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdcAtom is a Querydsl query type for IdcAtom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdcAtom extends EntityPathBase<IdcAtom> {

    private static final long serialVersionUID = 763480235L;

    public static final QIdcAtom idcAtom = new QIdcAtom("idcAtom");

    public final StringPath atomIndicatorCode = createString("atomIndicatorCode");

    public final StringPath atomIndicatorName = createString("atomIndicatorName");

    public final StringPath dataSheet = createString("dataSheet");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath describeInfo = createString("describeInfo");

    public final StringPath expression = createString("expression");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> indicatorStatus = createNumber("indicatorStatus", Integer.class);

    public final StringPath sqlSentence = createString("sqlSentence");

    public final StringPath themeCode = createString("themeCode");

    public QIdcAtom(String variable) {
        super(IdcAtom.class, forVariable(variable));
    }

    public QIdcAtom(Path<? extends IdcAtom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdcAtom(PathMetadata metadata) {
        super(IdcAtom.class, metadata);
    }

}

