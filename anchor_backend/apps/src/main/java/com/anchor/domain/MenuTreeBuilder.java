package com.anchor.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuTreeBuilder {

    public static List<MenuItem> buildTree(List<MenuItem> menuItems) {
        if (menuItems == null) {
            return null;
        }
        
        // 使用Map来存储所有菜单项，便于快速查找
        Map<Long, MenuItem> menuMap = new HashMap<>();
        for (MenuItem menu : menuItems) {
            menuMap.put(menu.getId(), menu);
        }
        
        List<MenuItem> trees = new ArrayList<>();
        for (MenuItem menu : menuItems) {
            // 父节点ID为0的是顶级节点
            if (menu.getParentId() == 0) {
                trees.add(menu);
            } else {
                // 从Map中获取父节点，并添加当前节点作为子节点
                MenuItem parentNode = menuMap.get(menu.getParentId());
                if (parentNode != null) {
                    parentNode.addChild(menu);
                }
            }
        }
        return trees;
    }
}