package com.tencent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface DataDao {

    void recordSystemInfo(@Param("data") Map<String,Object> map);

}
