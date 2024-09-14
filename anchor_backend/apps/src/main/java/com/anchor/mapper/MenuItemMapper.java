package com.anchor.mapper;

import com.anchor.domain.MenuItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuItemMapper extends BaseMapper<MenuItem> {
//    @Select("SELECT id, name, icon, link, parent_id ,is_link FROM menu_items WHERE parent_id= #{id} and is_link = 1")
//    List<MenuItem>  findMenuItemById( String id);
//
//    @Select("SELECT id, name, icon, link, parent_id ,is_link \n" +
//            "  FROM menu_items\n" +
//            "  WHERE parent_id IS NULL")
//    List<MenuItem> findTopMenuItems();
//
//    @Insert({
//            "INSERT INTO menu_items (name, icon, link, parent_id, is_link)",
//            "VALUES ( #{name}, #{icon}, #{link}, #{parentId}, #{isLink})"
//    })
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    int saveMenuItem( MenuItem menuItem);
//
//    @Delete({"DELETE FROM menu_items WHERE id = #{id}"})
//    int deleteMenuItem(long id);
//
//    @Insert({
//            "<script>",
//            "INSERT INTO menu_items (id, name, icon, link, parent_id, is_link)",
//            "VALUES (#{id}, #{name}, #{icon}, #{link}, #{parentId}, #{isLink})",
//            "ON DUPLICATE KEY UPDATE",
//            "name = VALUES(name),",
//            "icon = VALUES(icon),",
//            "link = VALUES(link),",
//            "parent_id = VALUES(parent_id),",
//            "is_link = VALUES(is_link)",
//            "</script>"
//    })
//    int updateMenuItem(MenuItem menuItem);
//
//    @Select("SELECT id, name, icon, link, parent_id,is_link FROM menu_items")
//    List<MenuItem> findAll();
//
//    @Select("select id, name, icon, link, parent_id,is_link FROM menu_items where is_link = 1")
//    List<MenuItem> findSupMenu();

    @Select("SELECT id, menu_name, icon, path, parent_id, is_link FROM sys_menu WHERE parent_id = #{id} AND is_link = 1")
    List<MenuItem> findMenuItemById(String id);

    @Select("SELECT id, menu_name, icon, path, parent_id, is_link FROM sys_menu WHERE parent_id IS NULL")
    List<MenuItem> findTopMenuItems();

    @Insert({
            "INSERT INTO sys_menu (menu_name, icon, path, parent_id, is_link)",
            "VALUES (#{menuName}, #{icon}, #{path}, #{parentId}, #{isLink})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveMenuItem(MenuItem menuItem);

    @Delete({"DELETE FROM sys_menu WHERE id = #{id}"})
    int deleteMenuItem(long id);

    @Insert({
            "<script>",
            "INSERT INTO sys_menu (id, menu_name, icon, path, parent_id, is_link)",
            "VALUES (#{id}, #{menuName}, #{icon}, #{path}, #{parentId}, #{isLink})",
            "ON DUPLICATE KEY UPDATE",
            "menu_name = VALUES(menu_name),",
            "icon = VALUES(icon),",
            "path = VALUES(path),",
            "parent_id = VALUES(parent_id),",
            "is_link = VALUES(is_link)",
            "</script>"
    })
    int updateMenuItem(MenuItem menuItem);

    @Select("SELECT id, menu_name, icon, path, parent_id, is_link FROM sys_menu")
    List<MenuItem> findAll();

    @Select("SELECT id, menu_name, icon, path, parent_id, is_link FROM sys_menu WHERE is_link = 1")
    List<MenuItem> findSupMenu();
}