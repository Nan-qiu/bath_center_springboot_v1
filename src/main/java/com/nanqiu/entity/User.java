package com.nanqiu.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO,value = "user_id")
    private Integer userId;
    private String userName;
    private String sex;
    private Date birth;
    private String phone;
    private Integer money;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Integer isShower;
    private String password;
    @TableLogic
    private Integer isDelete;
}

