package com.nanqiu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanqiu.entity.History;
import com.nanqiu.entity.User;
import com.nanqiu.mapper.HistoryMapper;
import com.nanqiu.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class BathCenterApplicationTests {

//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private HistoryMapper historyMapper;
//    @Test
//    void contextLoads(){
//        User user = new User();
//        user.setUserId(1004);
//
//        QueryWrapper<History> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id",user.getUserId());
//        wrapper.eq("is_quit",0);
//        History history = historyMapper.selectOne(wrapper);
//        System.out.println(history);
//        history.setIsQuit(1);
//        historyMapper.update(history,wrapper);
//    }
//
//    @Test
//    void testPage(){
//        Page<User> page = new Page<>(1,3);
//        userMapper.selectPage(page,null);
//        System.out.println("-------------------------------------");
//        System.out.println(page.getSize());
//        System.out.println(page.getTotal());
//        System.out.println(page.getPages());
//        System.out.println(page.getMaxLimit());
//
//    }
//
//    @Test
//    void like(){
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.likeLeft("user_name","m");
//        userMapper.selectList(wrapper).forEach(System.out::println);
//    }

}
