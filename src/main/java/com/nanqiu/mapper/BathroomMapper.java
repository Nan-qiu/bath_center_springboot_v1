package com.nanqiu.mapper;

import com.nanqiu.cache.RedisCache;
import com.nanqiu.entity.Bathroom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nanqiu
 * @since 2021-12-13
 */
@Mapper
//@CacheNamespace(implementation = RedisCache.class,eviction = RedisCache.class)
public interface BathroomMapper extends BaseMapper<Bathroom> {

}
