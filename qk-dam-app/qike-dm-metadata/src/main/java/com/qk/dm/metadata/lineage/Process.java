package com.qk.dm.metadata.lineage;

import org.apache.atlas.model.instance.AtlasObjectId;

import java.util.Collection;

/**
 * @author zhudaoming
 */
public interface Process {
        /**
         * <p>血缘输入ids<p/>
         * @param typeName 类型
         * @param qualifiedName uniq name
         * @return Collection<AtlasObjectId>
         */
        Collection<AtlasObjectId> inputIds(String typeName, String[] qualifiedName);

        /**
         * <p>血缘输出ids<p/>
         * @param typeName 类型
         * @param qualifiedName uniq name
         * @return Collection<AtlasObjectId>
         */
        Collection<AtlasObjectId> outputIds(String typeName, String[] qualifiedName);
    }