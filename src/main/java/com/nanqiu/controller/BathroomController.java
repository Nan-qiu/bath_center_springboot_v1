package com.nanqiu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanqiu.entity.Bathroom;
import com.nanqiu.entity.History;
import com.nanqiu.entity.User;
import com.nanqiu.mapper.BathroomMapper;
import com.nanqiu.mapper.HistoryMapper;
import com.nanqiu.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
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
@Api(tags = "澡堂模块")
@RestController
@RequestMapping("/bathroom")
public class BathroomController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BathroomMapper bathroomMapper;
    @Autowired
    private HistoryMapper historyMapper;

    /**
     * @param massage 前端传来的userId,time和userName
     * @return
     */
    @ApiImplicitParam(name = "massage", value = "userId,bId和time", required = true)
    @ApiOperation(value = "用户进入澡堂")
    @PostMapping("/enter")
    public Map<String, Object> enter(@RequestBody Map<String, Object> massage){
        HashMap<String, Object> map = new HashMap<>();
        //把标志位isShower置1
        User curUser = userMapper.selectById((Integer) massage.get("userId"));
        if (curUser.getIsShower()==1){
            map.put("state",false);
            map.put("msg","请勿重复进入！cnd");
            return map;
        }
        curUser.setIsShower(1);
        //把bId存入user的inBath
        curUser.setInBath((Integer) massage.get("bId"));
        userMapper.updateById(curUser);

        //当前澡堂人数+1
        Bathroom bathroom = bathroomMapper.selectById((Integer) massage.get("bId"));
        Integer people = bathroom.getPeople();
        bathroom.setPeople(people + 1);
        bathroomMapper.updateById(bathroom);

        //生成过期时间
        Calendar timeout = Calendar.getInstance();
        timeout.add(Calendar.MINUTE,(Integer) massage.get("time"));

        //增加history记录,自动填充enterTime
        History history = new History();
        history.setUserId((Integer) massage.get("userId"));
        history.setUserName(curUser.getUserName());
        history.setBName(bathroom.getBName());
        history.setTimeout(timeout.getTime());
        history.setIsQuit(0);
        historyMapper.insert(history);

        map.put("state",true);
        map.put("msg",curUser.getUserName() + "已进入澡堂！");
        return map;
    }

    @ApiImplicitParam(name = "massage", value = "userId", required = true)
    @ApiOperation(value = "用户退出澡堂")
    @PostMapping("/quit")
    public Map<String, Object> quit(@RequestBody Map<String, Object> massage){
        HashMap<String, Object> map = new HashMap<>();
        //把标志位isShower置0
        User curUser = userMapper.selectById((Integer) massage.get("userId"));
        if(curUser.getIsShower()==0){
            map.put("state",false);
            map.put("msg",curUser.getUserName() + "未进入澡堂！");
            return map;
        }
        curUser.setIsShower(0);
        userMapper.updateById(curUser);

        //当前澡堂人数-1
        Bathroom bathroom = bathroomMapper.selectById(curUser.getInBath());
        Integer people = bathroom.getPeople();
        bathroom.setPeople(people - 1);
        bathroomMapper.updateById(bathroom);

        //增加quitTime
        QueryWrapper<History> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", massage.get("userId"));
        wrapper.eq("is_quit",0);
        History history = historyMapper.selectOne(wrapper);
        System.out.println(history);
        history.setIsQuit(1);
        historyMapper.update(history,wrapper);

        map.put("state",true);
        map.put("msg",curUser.getUserName() + "已退出澡堂！");
        return map;
    }

    @GetMapping("/queryAll")
    @ApiOperation(value = "查询所有澡堂信息")
    public List<Bathroom> queryAll(){
        return bathroomMapper.selectList(null);
    }

}

