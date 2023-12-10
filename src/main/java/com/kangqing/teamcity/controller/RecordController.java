package com.kangqing.teamcity.controller;

import com.kangqing.teamcity.entity.Record;
import com.kangqing.teamcity.service.RecordService;
import com.kangqing.teamcity.util.JsonResult;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author kangqing
 * @since 2023/8/27 20:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/getAll")
    public JsonResult<?> getAll(Record record, @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "5") int pageSize) {
        final Page<Record> list = recordService.getAll(record, page, pageSize);
        return JsonResult.success(list);
    }

    @PostMapping
    public JsonResult<?> add(@RequestBody @Valid Record record) {
        recordService.add(record);
        return JsonResult.success("新增成功");
    }

    @GetMapping("/getValue/{type}")
    public JsonResult<?> getValue(@PathVariable Integer type) {
        return JsonResult.success(recordService.getValue(type));
    }

    /**
     * 查询金额
     * @return 金额
     */
    @GetMapping("/account")
    public JsonResult<?> getCount(Record record) {
        return JsonResult.success(recordService.getCount(record));
    }



}
