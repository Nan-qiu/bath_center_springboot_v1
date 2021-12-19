package com.nanqiu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanqiu.entity.History;
import com.nanqiu.entity.User;
import com.nanqiu.mapper.HistoryMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nanqiu
 * @since 2021-12-13
 */
@Api(tags = "历史记录模块")
@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryMapper historyMapper;


    @ApiImplicitParam(name = "curPage", value = "查询的页数", required = true)
    @ApiOperation(value = "查询所有的历史记录")
    @GetMapping("/queryAll")
    public Map<String, Object> queryAll(int curPage){
        HashMap<String, Object> map = new HashMap<>();
        Page<History> page = new Page<>(curPage,10);

        QueryWrapper<History> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("quit_time");
        wrapper.orderByDesc("quit_time");
        Page<History> historyPage = historyMapper.selectPage(page,wrapper);

        map.put("historyList",historyPage.getRecords());
        map.put("maxPage",historyPage.getPages());
        map.put("total",historyPage.getTotal());
        return map;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "查询的姓名",required = true),
            @ApiImplicitParam(name = "curPage",value = "页数",required = true)})
    @ApiOperation(value = "模糊查询对应user_name的历史记录")
    @GetMapping("/queryByName")
    public Map<String, Object> queryHistoryByName(String name,int curPage){
        HashMap<String, Object> map = new HashMap<>();

        Page<History> page = new Page<>(curPage,10);
        QueryWrapper<History> wrapper = new QueryWrapper<>();
        wrapper.like("user_name",name);

        Page<History> historyPage = historyMapper.selectPage(page,wrapper);
        map.put("historyList",historyPage.getRecords());
        map.put("maxPage",historyPage.getPages());
        map.put("total",historyPage.getTotal());

        return map;
    }
}

