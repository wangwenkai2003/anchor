package com.anchor.controller;


import com.anchor.domain.GenTable;
//import com.anchor.domain.TableColumn;
import com.anchor.domain.GenTableColumn;
import com.anchor.service.IGenTableColumnService;
import com.anchor.service.IGenTableService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 代码生成 操作处理
 * 
 * @author ruoyi
 */
@Slf4j
@RestController
@RequestMapping("/gen")
public class GenController
{
    @Autowired
    private IGenTableService genTableService;

    @Autowired
    IGenTableColumnService tableCOLUMNService;

    @Autowired
    private Configuration codeGenerationFreemarkerConfig;


//    @Autowired
//    private IGenTableColumnService genTableColumnService;

    @GetMapping("/list")
    public List<GenTable> genList(GenTable genTable)
    {
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return list;
    }

    @GetMapping("/selectTableColumnListByName")
    public List<GenTableColumn> genTables(String tableName)
    {
        List<GenTableColumn> tableCOLUMNS = tableCOLUMNService.selectDbTableColumnsByName(tableName);
        return tableCOLUMNS;
    }

    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException
    {
        GenTable genTable = genTableService.selectByTableName(tableName);
        log.info(String.valueOf(genTable));
    }

    @PostMapping("/codeGen")
    public String codeGen(@RequestBody GenTable genTable) throws IOException, TemplateException {
//        // 获取模板
//        Template template = codeGenerationFreemarkerConfig.getTemplate("db/index.sql.ftl");
//
//        // 创建一个 StringWriter 用来接收渲染后的字符串
//        StringWriter stringWriter = new StringWriter();
//
//        // 准备数据模型
//        Map<String, Object> dataModel = new HashMap<>();
//        dataModel.put("genTable", genTable); // 将 GenTable 对象传递给模板
//
//        // 渲染模板到 StringWriter
//        template.process(dataModel, stringWriter);
//
//        // 获取文件输出路径和文件名
//        String outputPath = genTable.getGenPath();
//        String fileName = genTable.getFunctionName() + ".sql"; // 或者根据需要动态生成文件名
//        File outputFile = new File(outputPath, fileName);
//
//        // 写入文件
//        try (FileWriter fileWriter = new FileWriter(outputFile)) {
//            fileWriter.write(stringWriter.toString());
//        }

        String s = genTableService.codeGen(genTable);

        // 返回渲染后的字符串或成功消息
        return s;
    }

}