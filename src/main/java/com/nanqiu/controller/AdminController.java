package com.nanqiu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Constants;
import com.nanqiu.entity.Admin;
import com.nanqiu.mapper.AdminMapper;
import com.nanqiu.utils.JWTUtils;
import com.nanqiu.utils.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Api("管理员模块")
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @ApiImplicitParam(name = "admin", value = "管理员对象", required = true)
    @ApiOperation(value = "管理员登录")
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Admin admin,HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();

        String imageCode = admin.getImageCode();
        if (imageCode==null){
            map.put("state",false);
            map.put("msg","验证码不能为空！");
            return map;
        }else{
            //String imageCodeKey = request.getHeader("ImageCodeKey");
            String imageCodeKey = null;
            for(Cookie cookie : request.getCookies()){
                if (cookie.getName().equals("ImageCodeKey")){
                    imageCodeKey = cookie.getValue();
                    break;
                }
            }
            if(imageCodeKey==null){
                map.put("state",false);
                map.put("msg","验证码校验失败！");
                return map;
            }

            System.out.println("imageCodeKey->"+imageCodeKey);
            String verifiedCode = (String) redisTemplate.opsForValue().get(Constants.KAPTCHA_SESSION_KEY + imageCodeKey);
            if(verifiedCode==null){
                map.put("state",false);
                map.put("msg","验证码无效，请刷新验证码后重试！");
                return map;
            }
            if (Objects.equals(verifiedCode,imageCode.toLowerCase())){
                map.put("state",true);
                redisTemplate.delete(Constants.KAPTCHA_SESSION_KEY + imageCodeKey);
            } else{
                map.put("state",false);
                map.put("msg","验证码错误！");
                return map;
            }
        }
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

    @GetMapping("/verification")
    public void createImageCode(HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        BufferedImage image = new BufferedImage(300,75,BufferedImage.TYPE_INT_RGB);
        String randomText = VerifyCodeUtils.drawRandomText(image);
        System.out.println("验证码->"+randomText);
        //验证码id
        String ImageCodeKey = UUID.randomUUID().toString();
        //response.setHeader("ImageCodeKey",ImageCodeKey);

        //设置cookie
        Cookie cookie = new Cookie("ImageCodeKey",ImageCodeKey);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        response.addCookie(cookie);

        redisTemplate.opsForValue().set(Constants.KAPTCHA_SESSION_KEY+ImageCodeKey,randomText.toLowerCase(),5,TimeUnit.MINUTES);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"png",outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
