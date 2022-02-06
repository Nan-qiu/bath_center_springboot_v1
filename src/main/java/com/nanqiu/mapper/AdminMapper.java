package com.nanqiu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanqiu.cache.RedisCache;
import com.nanqiu.entity.Admin;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@CacheNamespace(implementation = RedisCache.class,eviction = RedisCache.class)
public interface AdminMapper extends BaseMapper<Admin> {
}
