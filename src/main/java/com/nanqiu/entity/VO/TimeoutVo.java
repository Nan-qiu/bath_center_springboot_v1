package com.nanqiu.entity.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
@Data
public class TimeoutVo {
    private Integer userId;
    private String userName;
    private String bName;
    private Date timeout;
}
