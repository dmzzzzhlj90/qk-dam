package com.qk.dm.dataquality.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDqcRuleDir is a Querydsl query type for DqcRuleDir
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDqcRuleDir extends EntityPathBase<DqcRuleDir> {

    private static final long serialVersionUID = -451326513L;

    public static final QDqcRuleDir dqcRuleDir = new QDqcRuleDir("dqcRuleDir");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parentId = createString("parentId");

    public final StringPath ruleDirId = createString("ruleDirId");

    public final StringPath ruleDirName = createString("ruleDirName");

    public QDqcRuleDir(String variable) {
        super(DqcRuleDir.class, forVariable(variable));
    }

    public QDqcRuleDir(Path<? extends DqcRuleDir> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDqcRuleDir(PathMetadata metadata) {
        super(DqcRuleDir.class, metadata);
    }

}

