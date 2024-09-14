package com.anchor.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class GenTable implements Serializable {
    private Long tableId;

    private String tableName;

    private String tableComment;

    private String subTableName;

    private String subTableFkName;

    private String className;

    private String tplCategory;

    private String packageName;

    private String moduleName;

    private String businessName;

    private String functionName;

    private String functionAuthor;

    private String genType;

    private String genPath;

    private String options;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String remark;

    private Integer parentId;

    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tableId=").append(tableId);
        sb.append(", tableName=").append(tableName);
        sb.append(", tableComment=").append(tableComment);
        sb.append(", subTableName=").append(subTableName);
        sb.append(", subTableFkName=").append(subTableFkName);
        sb.append(", className=").append(className);
        sb.append(", tplCategory=").append(tplCategory);
        sb.append(", packageName=").append(packageName);
        sb.append(", moduleName=").append(moduleName);
        sb.append(", businessName=").append(businessName);
        sb.append(", functionName=").append(functionName);
        sb.append(", functionAuthor=").append(functionAuthor);
        sb.append(", genType=").append(genType);
        sb.append(", genPath=").append(genPath);
        sb.append(", options=").append(options);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}