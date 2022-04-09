package com.qk.dm.indicator.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdcDerived is a Querydsl query type for IdcDerived
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdcDerived extends EntityPathBase<IdcDerived> {

    private static final long serialVersionUID = 928723075L;

    public static final QIdcDerived idcDerived = new QIdcDerived("idcDerived");

    public final StringPath associatedFields = createString("associatedFields");

    public final StringPath atomIndicatorCode = createString("atomIndicatorCode");

    public final StringPath dataSheet = createString("dataSheet");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath derivedIndicatorCode = createString("derivedIndicatorCode");

    public final StringPath derivedIndicatorName = createString("derivedIndicatorName");

    public final StringPath generalLimit = createString("generalLimit");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> indicatorStatus = createNumber("indicatorStatus", Integer.class);

    public final StringPath sqlSentence = createString("sqlSentence");

    public final StringPath themeCode = createString("themeCode");

    public final StringPath timeLimit = createString("timeLimit");

    public QIdcDerived(String variable) {
        super(IdcDerived.class, forVariable(variable));
    }

    public QIdcDerived(Path<? extends IdcDerived> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdcDerived(PathMetadata metadata) {
        super(IdcDerived.class, metadata);
    }

}

