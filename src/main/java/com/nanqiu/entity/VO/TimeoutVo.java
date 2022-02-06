package com.nanqiu.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeoutVo {
    private Integer userId;
    private String userName;
    private String bName;
    private Date timeout;
}
