package com.nanqiu.mapper;

import com.nanqiu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanqiu.entity.VO.TimeoutVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nanqiu
 * @since 2021-12-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
