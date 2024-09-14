package ${genTable.packageName};

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

import lombok.Data;

/**
* ${className} 实体类
*
* @author ${functionAuthor}
*/
@Data
public class ${className} {


<#list tableColumns as column>
    <#if column.javaField == "isIncrement">
        <@TableId "value= ${column.columnName}", type=IdType.AUTO />
    </#if>
private ${column.javaType} ${column.javaField};


</#list>
}


