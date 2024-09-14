package com.anchor.service;

import com.anchor.domain.GenTable;
import freemarker.template.TemplateException;
//import com.anchor.domain.TableColumn;


import java.io.IOException;
import java.util.List;

/**
 * 业务 服务层
 * 
 * @author ruoyi
 */
public interface IGenTableService
{
    /**
     * 查询业务列表
     * 
     * @param genTable 业务信息
     * @return 业务集合
     */
    public List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * 查询据库列表
     * 
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableList(GenTable genTable);

    /**
     * 查询据库列表
     * 
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableListByNames(String[] tableNames);

//    public List<TableCOLUMNServiceImpl> selectTableColumnListByName(String tableName);
    public GenTable selectByTableName(String tableName);

    public String codeGen( GenTable genTable) throws TemplateException, IOException;
}
