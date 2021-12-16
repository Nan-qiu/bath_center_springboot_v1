package com.nanqiu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanqiu.entity.User;
import com.nanqiu.entity.VO.TimeoutVo;
import com.nanqiu.mapper.BathroomMapper;
import com.nanqiu.mapper.HistoryMapper;
import com.nanqiu.mapper.UserMapper;
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

    @ApiImplicitParam(name = "user",value = "用户对象",required = true)
    @ApiOperation(value = "通过登录表单生成一个user对象，再查询")
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user){
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getUserId());
        wrapper.eq("password",user.getPassword());
        if (userMapper.selectOne(wrapper) == null){
            map.put("state",false);
            map.put("msg","用户名或密码错误！");
        } else{
            map.put("user",userMapper.selectOne(wrapper));
            map.put("state",true);
            map.put("msg","登陆成功！");
        }
        return map;
    }

    @ApiImplicitParam(name = "user",value = "用户对象",required = true)
    @ApiOperation(value = "通过注册表单生成一个user对象，再插入")
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user){
        userMapper.insert(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("state",true);
        map.put("msg","注册成功！");
        return map;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户对象",required = true),
            @ApiImplicitParam(name = "money",value = "要充值的金额",required = true)})
    @ApiOperation(value = "通过传入的user对象确定用户，并充值money元")
    @PostMapping("/charge")
    public Map<String, Object> charge(@RequestBody User user,int money){
        HashMap<String, Object> map = new HashMap<>();
        User curUser = userMapper.selectById(user.getUserId());
        int nMoney = curUser.getMoney() + money;
        curUser.setMoney(nMoney);
        userMapper.updateById(curUser);
        map.put("state",true);
        map.put("msg","充值成功！");
        return map;
    }

    /**
     * @param user     传入的user对象
     * @param spendWay 传入的套餐：
     *                 spendWay=1，为30分钟套餐，10元；
     *                 spendWay=2，为60分钟套餐，18元；
     *                 spendWay=3，为90分钟套餐，25元
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户对象",required = true),
            @ApiImplicitParam(name = "spendWay",value = "选择的套餐",required = true)})
    @ApiOperation(value =
            "通过传入的user对象确定用户，并选择要那种套餐\n" +
            "spendWay=1，为30分钟套餐，10元\n" +
            "spendWay=2，为60分钟套餐，18元\n" +
            "spendWay=3，为90分钟套餐，25元")
    @PostMapping("/spend")
    public Map<String, Object> spend(@RequestBody User user,int spendWay){
        HashMap<String, Object> map = new HashMap<>();
        //选择套餐和返回的超时时间
        int spendMoney=0,time = 0;
        if (spendWay == 1) {
            spendMoney = 10;
            time = 30;
        }
        else if (spendWay == 2){
            spendMoney = 18;
            time = 60;
        }
        else if (spendWay == 3) {
            spendMoney = 25;
            time = 90;
        }
        User curUser = userMapper.selectById(user.getUserId());
        int nMoney = curUser.getMoney();
        if (nMoney < spendMoney){
            map.put("state",false);
            map.put("msg","余额不足！");
        } else{
            map.put("state",true);
            map.put("msg","已支付，欢迎下次光临！");
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
    public Map<String,Object> queryAll(int curPage){
        HashMap<String, Object> map = new HashMap<>();
        Page<User> page = new Page<>(curPage,10);
        Page<User> userPage = userMapper.selectPage(page,null);
        map.put("userList",userPage.getRecords());
        map.put("maxPage",userPage.getPages());
        map.put("total",userPage.getTotal());
        return map;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "查询的名字",required = true),
            @ApiImplicitParam(name = "curPage",value = "查询的页数",required = true)})
    @ApiOperation(value = "通过输入的name模糊查询对应的user_name，curPage为查询的页数")
    @GetMapping("/queryByName")
    public Map<String,Object> queryUserByName(String name,int curPage){
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

    @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    @ApiOperation(value = "删除用户")
    @GetMapping("/delete")
    public Map<String,Object> deleteById(int userId){
        HashMap<String, Object> map = new HashMap<>();
        userMapper.deleteById(userId);
        map.put("state",true);
        map.put("msg","删除成功！");
        return map;
    }

}

