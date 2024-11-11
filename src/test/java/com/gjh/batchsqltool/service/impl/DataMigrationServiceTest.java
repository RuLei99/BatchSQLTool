package com.gjh.batchsqltool.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DataMigrationServiceTest {

    @Autowired
    private DataMigrationService dataMigrationService;

    @Test
    void migrateData() {
        dataMigrationService.migrateData();
    }
}