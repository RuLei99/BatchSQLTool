package com.gjh.batchsqltool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gjh.batchsqltool.mapper.TestOldMapper;
import com.gjh.batchsqltool.model.TestNew;
import com.gjh.batchsqltool.model.TestOld;
import com.gjh.batchsqltool.service.TestNewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataMigrationService {

    @Resource
    private TestOldMapper testOldMapper;

    @Resource
    private TestNewService testNewService;

    @Transactional
    public void migrateData() {
        //2023年11月11日 00:00:00
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2023, Calendar.NOVEMBER, 11, 0, 0, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startCalendar.getTime();

        // 2024年11月11日 23:59:59
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(2024, Calendar.NOVEMBER, 11, 23, 59, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);
        Date endDate = endCalendar.getTime();

        // 每次迁移15天的数据
        while (startDate.before(endDate)) {
            // 计算每次批次的结束时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1); //1天
            Date batchEndDate = calendar.getTime();

            // 创建查询条件
            QueryWrapper<TestOld> queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("create_time", startDate)
                    .lt("create_time", batchEndDate);

            // 获取旧表中当前批次的数据
            List<TestOld> records = testOldMapper.selectList(queryWrapper);

            if (!records.isEmpty()) {
                // 批量插入到新表
                List<TestNew> newRecords = records.stream().map(record -> {
                    TestNew newRecord = new TestNew();
                    newRecord.setId(record.getId());
                    newRecord.setUsername(record.getUsername());
                    newRecord.setPhone(record.getPhone());
                    newRecord.setEmail(record.getEmail());
                    newRecord.setCreateTime(record.getCreateTime());
                    newRecord.setIsDeleted(record.getIsDeleted());
                    return newRecord;
                }).collect(Collectors.toList());

                testNewService.saveBatch(newRecords, 1000); // 每次插入1000条
            }
            startDate = batchEndDate;
        }
    }
}
