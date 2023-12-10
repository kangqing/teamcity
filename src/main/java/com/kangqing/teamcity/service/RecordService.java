package com.kangqing.teamcity.service;

import cn.hutool.core.date.DateTime;
import com.kangqing.teamcity.entity.Record;
import com.kangqing.teamcity.mapper.RecordMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kangqing.teamcity.entity.table.RecordTableDef.RECORD;

/**
 * @author kangqing
 * @since 2023/8/27 20:09
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordMapper recordMapper;

    /**
     * 查询所有记录
     * @param record 查询条件
     * @param page 第几页
     * @param pageSize 页容量
     */
    public Page<Record> getAll(Record record, int page, int pageSize) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .from(RECORD)
                .where(RECORD.ID.eq(record.getId()))
                .and(RECORD.NAME.like(record.getName()))
                .and(RECORD.TYPE.eq(record.getType()))
                .and(RECORD.FRIENDS.eq(record.getFriends()))
                .orderBy(RECORD.CREATE_TIME.desc(), RECORD.ID.asc());
        return recordMapper.paginate(page, pageSize, queryWrapper);
    }

    public void add(Record record) {
        record.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        recordMapper.insert(record);
    }

    public String getValue(Integer type) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .from(RECORD)
                .and(RECORD.TYPE.eq(type));
        final List<Record> recordList = recordMapper.selectListByQuery(queryWrapper);
        Integer res = recordList.stream().mapToInt(Record::getMoney).sum();
        return String.valueOf(res);
    }

    /**
     * 查询金额
     * @param record 查询条件
     * @return 金额正负
     */
    public String getCount(Record record) {
        QueryWrapper queryWrapper;
        if (record == null) {
            queryWrapper = QueryWrapper.create().select();

        } else {
            queryWrapper = QueryWrapper.create()
                    .select()
                    .from(RECORD)
                    .where(RECORD.ID.eq(record.getId()))
                    .and(RECORD.NAME.like(record.getName()))
                    .and(RECORD.TYPE.eq(record.getType()))
                    .and(RECORD.FRIENDS.eq(record.getFriends()))
                    .orderBy(RECORD.CREATE_TIME.desc(), RECORD.ID.asc());
        }
        final List<Record> recordList = recordMapper.selectListByQuery(queryWrapper);
        final Integer jin = Optional.ofNullable(recordList).orElse(new ArrayList<>())
                .stream().filter(e -> e.getType() == 1)
                .map(Record::getMoney).reduce(Integer::sum).orElse(0);
        final Integer chu = Optional.ofNullable(recordList).orElse(new ArrayList<>())
                .stream().filter(e -> e.getType() == 0)
                .map(Record::getMoney).reduce(Integer::sum).orElse(0);
        return "入账：" + jin + "\n出账：" + chu + "\n总账：" + (jin - chu);
    }
}
