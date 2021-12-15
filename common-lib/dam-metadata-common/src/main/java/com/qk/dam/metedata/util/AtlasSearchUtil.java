package com.qk.dam.metedata.util;

import com.qk.dam.metedata.config.AtlasConfig;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntityHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AtlasSearchUtil {
    private AtlasSearchUtil() {
        throw new IllegalStateException("Utility search");
    }

    private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();


    public static List<AtlasEntityHeader> getEntitiesByAttr(String typeName, String dbName, String tableName, String server){
        SearchParameters.FilterCriteria filterCriteria = new SearchParameters.FilterCriteria();
        filterCriteria.setCondition(SearchParameters.FilterCriteria.Condition.AND);
        List<SearchParameters.FilterCriteria> criterion = new ArrayList<>();
        SearchParameters.FilterCriteria db = new SearchParameters.FilterCriteria();
        db.setOperator(SearchParameters.Operator.STARTS_WITH);
        db.setAttributeName("qualifiedName");
        if(Objects.nonNull(tableName)){
            db.setAttributeValue(String.join(".",dbName,tableName));
        } else {
            db.setAttributeValue(dbName);
        }
        criterion.add(db);
        SearchParameters.FilterCriteria table = new SearchParameters.FilterCriteria();
        table.setOperator(SearchParameters.Operator.ENDS_WITH);
        table.setAttributeName("qualifiedName");
        table.setAttributeValue(server);
        criterion.add(table);
        filterCriteria.setCriterion(criterion);
        return getEntities(typeName,filterCriteria);
    }

    private static List<AtlasEntityHeader> getEntities(String typeName,SearchParameters.FilterCriteria filterCriteria){
        try{
            AtlasSearchResult atlasSearchResult = atlasClientV2.basicSearch(typeName, filterCriteria, null, null, false, 1000, 0);
            return atlasSearchResult.getEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
