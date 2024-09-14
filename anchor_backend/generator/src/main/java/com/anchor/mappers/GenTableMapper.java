package com.anchor.mappers;

import com.anchor.domain.GenTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GenTableMapper extends BaseMapper<GenTable> {


    public List<GenTable> selectGenTableList( GenTable genTable);


    int deleteByPrimaryKey(Long tableId);

    int insert(GenTable record);

    GenTable selectByPrimaryKey(Long tableId);

    List<GenTable> selectAll();

    int updateByPrimaryKey(GenTable record);
}