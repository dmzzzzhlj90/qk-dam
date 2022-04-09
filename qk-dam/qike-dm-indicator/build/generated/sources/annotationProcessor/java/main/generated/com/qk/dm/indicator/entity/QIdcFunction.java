package com.qk.dm.indicator.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdcFunction is a Querydsl query type for IdcFunction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdcFunction extends EntityPathBase<IdcFunction> {

    private static final long serialVersionUID = -888658382L;

    public static final QIdcFunction idcFunction = new QIdcFunction("idcFunction");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath engine = createString("engine");

    public final StringPath function = createString("function");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> parentId = createNumber("parentId", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final StringPath typeName = createString("typeName");

    public QIdcFunction(String variable) {
        super(IdcFunction.class, forVariable(variable));
    }

    public QIdcFunction(Path<? extends IdcFunction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdcFunction(PathMetadata metadata) {
        super(IdcFunction.class, metadata);
    }

}

