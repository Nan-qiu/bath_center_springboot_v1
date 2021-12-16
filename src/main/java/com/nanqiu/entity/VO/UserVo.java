package com.nanqiu.entity.VO;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class UserVo {
    private Integer userId;
    private String userName;
    private String sex;
    private Date birth;
    private String phone;
    private Integer money;
    private Integer isShower;
}
