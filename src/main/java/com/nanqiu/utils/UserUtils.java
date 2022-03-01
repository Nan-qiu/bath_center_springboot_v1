package com.nanqiu.utils;

import com.nanqiu.entity.User;
import com.nanqiu.entity.VO.UserVo;

public class UserUtils {
    public static UserVo toUserVo(User user){
        return UserVo.builder().
                        userId(user.getUserId()).
                        userName(user.getUserName()).
                        sex(user.getSex()).
                        birth(user.getBirth()).
                        phone(user.getPhone()).
                        money(user.getMoney()).
                        isShower(user.getIsShower()).
                        inBath(user.getInBath()).
                        is_delete(user.getIsDelete())
                .build();
    }
}
