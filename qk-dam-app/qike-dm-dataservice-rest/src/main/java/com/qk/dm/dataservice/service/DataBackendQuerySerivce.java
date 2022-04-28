package com.qk.dm.dataservice.service;

import com.qk.dam.cache.CacheManagerEnum;
import com.qk.dam.cache.RestCache;
import com.qk.dam.model.HttpDataParamModel;

public interface DataBackendQuerySerivce {

    @RestCache(
            value = CacheManagerEnum.CACHE_NAME_DAS_REST,
            key = "#httpDataParamModel.toString()")
    Object dataBackendQuery(HttpDataParamModel httpDataParamModel);
}