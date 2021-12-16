package com.nanqiu.service.impl;

import com.nanqiu.entity.User;
import com.nanqiu.mapper.UserMapper;
import com.nanqiu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nanqiu
 * @since 2021-12-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
