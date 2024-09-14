package com.anchor.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@TableName( "menu_items")
public class MenuItem {


    private Long id;


//    private String name;
    private String menuName;


    private String icon;


//    private String link;
    private String path;

    private Long  parentId;

    private Integer isLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private List<MenuItem> children;

    // Getters and setters


    public void addChild(MenuItem child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    public void removeChild(MenuItem child) {
        child.setParentId(null); // 清空子菜单项的父级ID
        this.children.remove(child);
    }
}