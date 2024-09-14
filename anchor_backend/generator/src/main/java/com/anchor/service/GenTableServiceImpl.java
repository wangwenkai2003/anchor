package com.anchor.service;

import cn.hutool.extra.template.TemplateException;
import com.anchor.domain.GenTable;

import com.anchor.domain.GenTableColumn;
import com.anchor.mappers.GenTableColumnMapper;

import com.anchor.mappers.GenTableMapper;

import com.anchor.util.DbTypeToJavaTypeUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class GenTableServiceImpl implements IGenTableService
{
    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private Configuration codeGenerationFreemarkerConfig;

    private Map<String, Object> dataModel = new HashMap<>();


    @Override
    public List<GenTable> selectGenTableList(GenTable genTable) {
        List<GenTable> genTables = genTableMapper.selectGenTableList(genTable);
        return genTables;
    }

    @Override
    public List<GenTable> selectDbTableList(GenTable genTable) {
        return null;
    }

    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return null;
    }

    @Override
    public GenTable selectByTableName(String tableName) {
        return null;
    }


//    public List<TableColumn> selectTableColumnListByName(String tableName){
//        List<TableColumn> tableColumns = genTableMapper.selectTableColumnListByName(tableName);
//        return tableColumns;
//    }

    public String codeGen( GenTable genTable) throws freemarker.template.TemplateException, IOException {
        // 获取文件输出路径
        String outputFolder = genTable.getGenPath();

        Field[] fields = GenTable.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // 设置为可访问私有字段
            try {
                Object value = field.get(genTable);
                if (value != null) {
                    dataModel.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field: " + field.getName(), e);
            }
        }

        // 添加当前日期
        dataModel.put("updaDate", java.time.LocalDate.now().toString());

        dataModel.put("genTable", genTable);

        String className = genTable.getClassName();


        processTableCloumns(genTable.getTableName());
        // 处理 templates/db 文件夹
        processFtlFile("db", "index.sql", className+".sql",outputFolder);

        // 处理 domain 文件夹
        processFtlFile("java/domain", "domain.java", className+".java",outputFolder);

        // 处理 mapper 文件夹
        processFtlFile("java/mapper", "mapper.java",className+".java", outputFolder);

        // 处理 service/impl 文件夹
        processFtlFile("java/service/impl", "serviceImpl.java",className+".java", outputFolder);

        // 处理 vue 文件夹
        processFtlFile("vue", "index.vue", className+".vue",outputFolder);

        // 返回成功消息
        return "All files generated successfully.";
    }

    private void processFtlFile(String folderName, String ftlFileName,String outFileName ,String outputFolder)
            throws IOException, TemplateException, freemarker.template.TemplateException {


        // 创建一个 StringWriter 用来接收渲染后的字符串
        StringWriter stringWriter = new StringWriter();

        // 获取模板
        Template template = codeGenerationFreemarkerConfig.getTemplate(folderName + '/' +ftlFileName + ".ftl");

        // 渲染模板到 StringWriter
        template.process(dataModel, stringWriter);

        // 获取文件输出路径和文件名
        String outputPath = outputFolder + "/" + folderName + "/" + outFileName;

        // 创建输出文件夹，如果不存在的话
        File outputDir = new File(outputPath).getParentFile();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        // 写入文件
        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            fileWriter.write(stringWriter.toString());
        }
    }

    private void processTableCloumns(String tableName){
        List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);

        dataModel.put("tableName", tableName); // 表名
        dataModel.put("tableColumns", new ArrayList<>()); // 列的信息列表

        for (GenTableColumn genTableColumn : genTableColumns) {
            // 创建一个映射来存储单个列的信息
            Map<String, Object> columnInfo = new HashMap<>();

            // 获取 GenTableColumn 的所有属性并存入 columnInfo 映射
            columnInfo.put("columnId", genTableColumn.getColumnId());
            columnInfo.put("tableId", genTableColumn.getTableId());
            columnInfo.put("columnName", genTableColumn.getColumnName());
            columnInfo.put("columnComment", genTableColumn.getColumnComment());
            columnInfo.put("columnType", genTableColumn.getColumnType());
            // 添加 Java 类型
            String javaType = DbTypeToJavaTypeUtil.mapColumnTypeToJavaType(genTableColumn.getColumnType());
            columnInfo.put("javaType", javaType);
            genTableColumn.setJavaType(javaType); // 更新 GenTableColumn 的 javaType 属性
            String javaFieldName = underscoreToCamelCase(genTableColumn.getColumnName());
            columnInfo.put("javaField", javaFieldName);
            genTableColumn.setJavaField(javaFieldName);
            columnInfo.put("isPk", genTableColumn.getIsPk());
            columnInfo.put("isIncrement", genTableColumn.getIsIncrement());
            columnInfo.put("isRequired", genTableColumn.getIsRequired());
            columnInfo.put("isInsert", genTableColumn.getIsInsert());
            columnInfo.put("isEdit", genTableColumn.getIsEdit());
            columnInfo.put("isList", genTableColumn.getIsList());
            columnInfo.put("isQuery", genTableColumn.getIsQuery());
            columnInfo.put("queryType", genTableColumn.getQueryType());
            columnInfo.put("htmlType", genTableColumn.getHtmlType());
            columnInfo.put("dictType", genTableColumn.getDictType());
            columnInfo.put("sort", genTableColumn.getSort());

            // 将列信息添加到数据模型中
            ((List<Map<String, Object>>) dataModel.get("tableColumns")).add(columnInfo);
        }

    }
    private String underscoreToCamelCase(String underscoreString) {
        StringBuilder camelCaseBuilder = new StringBuilder();
        boolean nextUpperCase = false;

        for (char c : underscoreString.toCharArray()) {
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    camelCaseBuilder.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    camelCaseBuilder.append(Character.toLowerCase(c));
                }
            }
        }

        return camelCaseBuilder.toString();
    }
}