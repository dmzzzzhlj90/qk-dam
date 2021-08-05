package com.qk.dm.metadata;

import com.qk.dam.metedata.config.AtlasConfig;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author spj
 * @date 2021/8/5 4:36 下午
 * @since 1.0.0
 */
public class AtlasLabelsUtil {
    public AtlasLabelsUtil() {
        throw new IllegalStateException("Utility labels");
    }

    private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();

    public void setLabels(String guid, String labels) throws AtlasServiceException {
        atlasClientV2.setLabels(guid, getLabelSet(labels));
    }

    public void removeLabels(String guid, String labels) throws AtlasServiceException {
        atlasClientV2.removeLabels(guid, getLabelSet(labels));
    }

    public Set<String> getLabelSet(String labels) {
        return Arrays.stream(labels.split(",")).collect(Collectors.toSet());
    }

}
