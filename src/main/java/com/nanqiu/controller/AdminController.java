package com.nanqiu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanqiu.entity.Admin;
import com.nanqiu.mapper.AdminMapper;
import com.nanqiu.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api("管理员模块")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;

    @ApiImplicitParam(name = "admin", value = "管理员对象", required = true)
    @ApiOperation(value = "管理员登录")
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Admin admin){
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_name",admin.getAdminName());
        wrapper.eq("password",admin.getPassword());
        Admin curAdmin = adminMapper.selectOne(wrapper);
        if ( curAdmin == null){
            map.put("state",false);
            map.put("msg","用户名或密码错误！");
            return map;
        } else{
            map.put("adminId",curAdmin.getAdminId());
            map.put("state",true);
            map.put("msg","登陆成功！");
        }

        HashMap<String, String> payload = new HashMap<>();
        payload.put("adminId",curAdmin.getAdminId().toString());
        payload.put("adminName",curAdmin.getAdminName());
        String token = JWTUtils.createToken(payload);
        map.put("token",token);
        return map;
    }

    @ApiImplicitParam(name = "admin", value = "管理员对象", required = true)
    @ApiOperation(value = "注册管理员")
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Admin admin){
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_name",admin.getAdminName());
        if (adminMapper.selectCount(wrapper)==1){
            map.put("state",false);
            map.put("msg","用户名已存在！");
            return map;
        }
        adminMapper.insert(admin);
        map.put("state",true);
        map.put("msg","注册成功！");
        return map;
    }
    @ApiImplicitParam(name = "adminId", value = "管理员Id", required = true)
    @ApiOperation(value = "通过id获取管理员")
    @GetMapping("/getAdmin")
    public Map<String,Object> getAdmin(int adminId){
        HashMap<String, Object> map = new HashMap<>();
        Admin admin = adminMapper.selectById(adminId);
        map.put("admin",admin);
        map.put("state",true);
        return map;
    }

    @ApiImplicitParam(name = "adminId", value = "管理员id", required = true)
    @ApiOperation(value = "删除管理员")
    @GetMapping("/delete")
    public Map<String, Object> deleteById(int adminId){
        HashMap<String, Object> map = new HashMap<>();
        adminMapper.deleteById(adminId);
        map.put("state",true);
        map.put("msg","删除成功！");
        return map;
    }
}
