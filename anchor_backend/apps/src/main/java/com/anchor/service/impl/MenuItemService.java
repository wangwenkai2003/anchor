package com.anchor.service.impl;

import com.anchor.domain.MenuItem;
import com.anchor.mapper.MenuItemMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService extends ServiceImpl<MenuItemMapper,MenuItem> {
    @Autowired
    private  MenuItemMapper menuItemMapper;

    public List<MenuItem> getAllMenuItems() {
        return menuItemMapper.selectList(null);
    }

    public int createMenuItem(MenuItem menuItem) {
        return menuItemMapper.saveMenuItem(menuItem);
    }

    public int updateMenuItem(MenuItem menuItem) {
        int i = menuItemMapper.updateMenuItem(menuItem);
        return i;
    }

    public List<MenuItem> menuItemList(){
        return menuItemMapper.findAll();
    }

    public List<MenuItem> findMenuItemById(String id){
        if (id.equals("null")|| id.equals("")){
            return menuItemMapper.findTopMenuItems();
        }
        return menuItemMapper.findMenuItemById(id);
    }

    public Integer deleteMenuItem(Long id) {
        int i = menuItemMapper.deleteMenuItem(id);
        return i;
    }

    public List<MenuItem> findSupMenu(){
        List<MenuItem> supMenu = menuItemMapper.findSupMenu();
        return supMenu;
    }
}