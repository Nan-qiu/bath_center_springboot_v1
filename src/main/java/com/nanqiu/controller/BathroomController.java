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
import io.swagger.annotations.ApiImplicitParams;
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
     * @param user 前端传来的user对象
     * @param bId  澡堂的id
     * @param time /user/spend 传来的time套餐时间
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户对象", required = true),
            @ApiImplicitParam(name = "bId", value = "澡堂id", required = true),
            @ApiImplicitParam(name = "time", value = "套餐时间", required = true)})
    @ApiOperation(value =
            "通过传入的user确定用户，bId确定要进入的澡堂，time是/user/spend 传来的time套餐时间\n" +
                    "用户进入，用户is_shower置1，history的is_quit置0，当前澡堂人数+1，生成过期时间timeout,自动填充enter_time")
    @PostMapping("/enter")
    public Map<String, Object> enter(@RequestBody User user,int bId,int time){
        HashMap<String, Object> map = new HashMap<>();
        //把标志位isShower置1
        User curUser = userMapper.selectById(user.getUserId());
        curUser.setIsShower(1);
        userMapper.updateById(curUser);

        //当前澡堂人数+1
        Bathroom bathroom = bathroomMapper.selectById(bId);
        Integer people = bathroom.getPeople();
        bathroom.setPeople(people + 1);
        bathroomMapper.updateById(bathroom);

        //生成过期时间
        Calendar timeout = Calendar.getInstance();
        timeout.add(Calendar.MINUTE,time);

        //增加history记录,自动填充enterTime
        History history = new History();
        history.setUserId(user.getUserId());
        history.setUserName(user.getUserName());
        history.setBName(bathroom.getBName());
        history.setTimeout(timeout.getTime());
        history.setIsQuit(0);
        historyMapper.insert(history);

        map.put("state",true);
        map.put("msg",curUser.getUserName() + "已进入澡堂！");
        return map;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户对象", required = true),
            @ApiImplicitParam(name = "bId", value = "澡堂id", required = true)})
    @ApiOperation(value = "通过传入的user确定用户，bId确定要退出的澡堂，用户退出，is_shower置0，is_quit置1，当前澡堂人数-1,自动填充quit_time")
    @PostMapping("/quit")
    public Map<String, Object> quit(@RequestBody User user,int bId){
        HashMap<String, Object> map = new HashMap<>();
        //把标志位isShower置0
        User curUser = userMapper.selectById(user.getUserId());
        curUser.setIsShower(0);
        userMapper.updateById(curUser);

        //当前澡堂人数-1
        Bathroom bathroom = bathroomMapper.selectById(bId);
        Integer people = bathroom.getPeople();
        bathroom.setPeople(people - 1);
        bathroomMapper.updateById(bathroom);

        //增加quitTime
        QueryWrapper<History> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getUserId());
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

