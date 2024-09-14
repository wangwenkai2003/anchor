package com.anchor.util;

import java.io.IOException;
import java.util.Properties;

public class DbTypeToJavaTypeUtil {

    private static final Properties mappingProperties = new Properties();

    static {
        try {
            mappingProperties.load(DbTypeToJavaTypeUtil.class.getClassLoader().getResourceAsStream("typeConverter.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public static String mapColumnTypeToJavaType(String columnType) {
        // 移除括号内的长度等信息
        String baseType = columnType.split("\\(")[0];
        String javaType = mappingProperties.getProperty(baseType.toLowerCase());
        return javaType != null ? javaType : "Object";
    }
}