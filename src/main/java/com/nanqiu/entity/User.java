package com.nanqiu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("is_delete")
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
    private Integer inBath;
    private String password;
    @TableLogic
    private Integer isDelete;
}

