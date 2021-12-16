package com.nanqiu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private Integer userId;
    private String userName;
    private String bName;
    @TableField(fill = FieldFill.INSERT)
    private Date enterTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date quitTime;
    private Date timeout;
    private Integer isQuit;
}
