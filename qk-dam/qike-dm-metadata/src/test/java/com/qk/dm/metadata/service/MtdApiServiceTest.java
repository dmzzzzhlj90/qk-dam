package com.qk.dm.metadata.service;

import com.qk.dm.metadata.DmMetadataApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


@SpringBootTest(classes = DmMetadataApplication.class)
@Ignore
public class MtdApiServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    MtdLabelsService mtdLabelsService;
//
//    @BeforeMethod
//    public void setUp() {
//    }
//
//    @AfterMethod
//    public void tearDown() {
//    }

    @Test
    public void testGetAllEntityType() {
    }

    @Test
    public void testMtdDetail() {
    }

    @Test
    public void testGetTables() {
    }

    @Test
    public void testGetColumns() {
    }
}