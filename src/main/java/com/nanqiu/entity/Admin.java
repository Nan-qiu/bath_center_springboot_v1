package com.nanqiu.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @TableId(type = IdType.AUTO,value = "admin_id")
    private Integer adminId;
    private String adminName;
    private String sex;
    private Date birth;
    private String phone;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private String password;
    private Integer role;
    @TableLogic
    private Integer isDelete;
}
