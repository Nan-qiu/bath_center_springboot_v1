package com.nanqiu.entity.VO;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("is_delete")
@TableName("user")
public class UserVo {
    @TableId(type = IdType.AUTO,value = "user_id")
    private Integer userId;
    private String userName;
    private String sex;
    private Date birth;
    private String phone;
    private Integer money;
    private Integer isShower;
    private Integer inBath;
    @TableLogic
    private Integer is_delete;
}
