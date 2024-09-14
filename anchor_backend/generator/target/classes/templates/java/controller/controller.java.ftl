<#--
  controller.java.ftl - 用于生成Controller类
-->

<#assign className = table.className>
<#assign packageName = table.packageName>
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, path, is_link,  perms, icon, create_by, create_time, update_by, update_time, remark)
values('${genTable.functionName}', '${genTable.businessName}', '${genTable.businessName}/${genTable.businessName}/index', 0 ,':list', '#', 'admin', sysdate(), '', null, '${genTable.functionName}菜单');