package com.nanqiu.mapper;

import com.nanqiu.cache.RedisCache;
import com.nanqiu.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanqiu.entity.VO.TimeoutVo;
import org.apache.ibatis.annotations.CacheNamespace;
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
@CacheNamespace(implementation = RedisCache.class,eviction = RedisCache.class)
public interface HistoryMapper extends BaseMapper<History> {
    @Select("select user_id,user_name,b_name,timeout from history where NOW()>timeout and is_quit=0")
    List<TimeoutVo> queryTimeout();
}
