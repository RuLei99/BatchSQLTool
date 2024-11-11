package com.gjh.batchsqltool.service.impl;

import com.gjh.batchsqltool.model.TestOld;
import com.gjh.batchsqltool.service.TestNewService;
import com.gjh.batchsqltool.service.TestOldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DataToDBService {

    @Resource
    private TestOldService testOldService; // 使用 TestNewService 插入数据

    private static final Logger logger = Logger.getLogger(DataToDBService.class.getName());

    @Transactional
    public void insertData() {
        List<TestOld> testOldList = new ArrayList<>();

        // 创建 DateTimeFormatter 对象来解析日期字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将字符串转换为 LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse("2023-11-10 12:45:02", formatter);

        // 转换为 java.util.Date
        Date specificDate = java.sql.Timestamp.valueOf(localDateTime);

        // 插入1000条数据（模拟）
        for (int i = 0; i < 100000; i++) {
            TestOld testOld = new TestOld();
            testOld.setId(10200001 + i);
            testOld.setUsername("user" + i);
            testOld.setPhone("138000" + (100000 + i));
            testOld.setEmail("user" + i + "@example.com");
            testOld.setCreateTime(specificDate);  // 设置当前时间
            testOld.setIsDeleted(0);
            testOldList.add(testOld);
        }

        try {
            // 假设 testNewService 有 saveBatch 方法，也可以插入 TestOld 数据
            boolean result = testOldService.saveBatch(testOldList, 2000); // 每次插入500条数据

            if (result) {
                logger.info("成功插入 " + testOldList.size() + " 条数据到 TestOld 表。");
            } else {
                logger.warning("批量插入数据到 TestOld 表失败！");
            }
        } catch (Exception e) {
            // 异常处理
            logger.log(Level.SEVERE, "数据插入异常", e);
            throw new RuntimeException("数据插入失败，事务回滚！", e);
        }
    }
}
