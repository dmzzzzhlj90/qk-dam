package com.qk.dm.metadata.vo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MtdAtlasParamsVOTest {
    MtdAtlasParamsVO mtdAtlasParamsVO = new MtdAtlasParamsVO("query", "typeName", "classification", 0, 0);

    @Test
    public void testSetQuery() {
        mtdAtlasParamsVO.setQuery("query");
    }

    @Test
    public void testSetTypeName() {
        mtdAtlasParamsVO.setTypeName("typeName");
    }

    @Test
    public void testSetClassification() {
        mtdAtlasParamsVO.setClassification("classification");
    }

    @Test
    public void testSetLimit() {
        mtdAtlasParamsVO.setLimit(0);
    }

    @Test
    public void testSetOffse() {
        mtdAtlasParamsVO.setOffse(0);
    }

    @Test
    public void testEquals() {
        boolean result = mtdAtlasParamsVO.equals("o");
        Assert.assertEquals(result, true);
    }

    @Test
    public void testCanEqual() {
        boolean result = mtdAtlasParamsVO.canEqual("other");
        Assert.assertEquals(result, true);
    }

    @Test
    public void testHashCode() {
        int result = mtdAtlasParamsVO.hashCode();
        Assert.assertEquals(result, 0);
    }

    @Test
    public void testToString() {
        String result = mtdAtlasParamsVO.toString();
        Assert.assertEquals(result, "replaceMeWithExpectedResult");
    }

    @Test
    public void testBuilder() {
        MtdAtlasParamsVO.MtdAtlasParamsVOBuilder result = MtdAtlasParamsVO.builder();
        Assert.assertEquals(result, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme