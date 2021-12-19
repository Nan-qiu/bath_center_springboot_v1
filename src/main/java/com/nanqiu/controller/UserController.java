package com.nanqiu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanqiu.entity.User;
import com.nanqiu.entity.VO.TimeoutVo;
import com.nanqiu.entity.VO.UserVo;
import com.nanqiu.mapper.BathroomMapper;
import com.nanqiu.mapper.HistoryMapper;
import com.nanqiu.mapper.UserMapper;
import com.nanqiu.mapper.UserVoMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BathroomMapper bathroomMapper;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private UserVoMapper userVoMapper;

//    @ApiImplicitParam(name = "user",value = "用户对象",required = true)
//    @ApiOperation(value = "通过登录表单生成一个user对象，再查询")
//    @PostMapping("/login")
//    public Map<String, Object> login(@RequestBody User user){
//        HashMap<String, Object> map = new HashMap<>();
//        QueryWrapper<UserVo> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id",user.getUserId());
//        wrapper.eq("password",user.getPassword());
//        if (userVoMapper.selectOne(wrapper) == null){
//            map.put("state",false);
//            map.put("msg","用户名或密码错误！");
//        } else{
//            map.put("user",userVoMapper.selectOne(wrapper));
//            map.put("state",true);
//            map.put("msg","登陆成功！");
//        }
//        return map;
//    }

    @ApiImplicitParam(name = "user", value = "用户对象", required = true)
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user){
        userMapper.insert(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("state",true);
        map.put("msg","注册成功！");
        return map;
    }


    @ApiImplicitParam(name = "massage", value = "用户id和money", required = true)
    @ApiOperation(value = "用户充值")
    @PostMapping("/charge")
    public Map<String, Object> charge(@RequestBody Map<String, Object> massage){
        HashMap<String, Object> map = new HashMap<>();
        User curUser = userMapper.selectById((Integer) massage.get("userId"));
        int nMoney = curUser.getMoney() + (Integer) massage.get("money");
        curUser.setMoney(nMoney);
        userMapper.updateById(curUser);
        map.put("state",true);
        map.put("msg","充值成功！");
        return map;
    }

    /**
     * @param massage 传入的套餐和用户id：
     *                 spendWay=1，为30分钟套餐，10元；
     *                 spendWay=2，为60分钟套餐，18元；
     *                 spendWay=3，为90分钟套餐，25元
     */

    @ApiImplicitParam(name = "massage", value = "用户id和spendWay", required = true)
    @ApiOperation(value = "选择套餐")
    @PostMapping("/spend")
    public Map<String, Object> spend(@RequestBody Map<String, Object> massage){
        HashMap<String, Object> map = new HashMap<>();
        User curUser = userMapper.selectById((Integer) massage.get("userId"));
        if (curUser.getIsShower()==1){
            map.put("state",false);
            map.put("msg","请勿重复进入！cnd");
            return  map;
        }

        //选择套餐和返回的超时时间
        int spendMoney = 0, time = 0;
        if ((Integer) massage.get("spendWay") == 1){
            spendMoney = 10;
            time = 30;
        } else if ((Integer) massage.get("spendWay") == 2){
            spendMoney = 18;
            time = 60;
        } else if ((Integer) massage.get("spendWay") == 3){
            spendMoney = 25;
            time = 90;
        }

        int nMoney = curUser.getMoney();
        if (nMoney < spendMoney){
            map.put("state",false);
            map.put("msg","余额不足！");
        } else{
            map.put("state",true);
            map.put("msg","已支付，请进澡堂！");
            map.put("time",time);
            nMoney -= spendMoney;
        }
        curUser.setMoney(nMoney);
        userMapper.updateById(curUser);
        return map;
    }


    @ApiOperation(value = "查询超过套餐时间还没出来的用户")
    @GetMapping("/queryTimeout")
    public List<TimeoutVo> queryTimeout(){
        return historyMapper.queryTimeout();
    }


    @ApiOperation(value = "查询所有用户")
    @GetMapping("/queryAll")
    public Map<String, Object> queryAll(int curPage){
        HashMap<String, Object> map = new HashMap<>();
        Page<UserVo> page = new Page<>(curPage,10);
        Page<UserVo> userPage = userVoMapper.selectPage(page,null);
        map.put("userList",userPage.getRecords());
        map.put("maxPage",userPage.getPages());
        map.put("total",userPage.getTotal());
        return map;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "查询的名字", required = true),
            @ApiImplicitParam(name = "curPage", value = "查询的页数", required = true)})
    @ApiOperation(value = "输入name模糊查询")
    @GetMapping("/queryByName")
    public Map<String, Object> queryUserByName(String name,int curPage){
        HashMap<String, Object> map = new HashMap<>();
        Page<User> page = new Page<>(curPage,10);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("user_name",name);
        Page<User> userPage = userMapper.selectPage(page,wrapper);
        map.put("userList",userPage.getRecords());
        map.put("maxPage",userPage.getPages());
        map.put("total",userPage.getTotal());

        return map;
    }

    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @ApiOperation(value = "删除用户")
    @GetMapping("/delete")
    public Map<String, Object> deleteById(int userId){
        HashMap<String, Object> map = new HashMap<>();
        userMapper.deleteById(userId);
        map.put("state",true);
        map.put("msg","删除成功！");
        return map;
    }

}

