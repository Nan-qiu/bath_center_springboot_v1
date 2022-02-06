package com.nanqiu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanqiu.cache.RedisCache;
import com.nanqiu.entity.VO.UserVo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

@Mapper
@CacheNamespace(implementation = RedisCache.class,eviction = RedisCache.class)
public interface UserVoMapper extends BaseMapper<UserVo> {
}
