package com.gjh.batchsqltool.construct;

import com.gjh.batchsqltool.service.impl.DataMigrationService;
import com.gjh.batchsqltool.service.impl.DataToDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class DataMigrationServiceRunner {

    @Resource
    private DataMigrationService dataMigrationService;

    @Resource
    private DataToDBService dataToDBService;

    @PostConstruct
    public void runMigrationOnStartup() {
        dataMigrationService.migrateData();
    }


//    @PostConstruct
//    public void DataToDBService() {
//        dataToDBService.insertData();
//    }
}
