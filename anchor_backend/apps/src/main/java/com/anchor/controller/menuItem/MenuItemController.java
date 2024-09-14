package com.anchor.controller.menuItem;

import com.anchor.domain.MenuItem;
import com.anchor.domain.MenuTreeBuilder;
import com.anchor.service.impl.MenuItemService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {
    @Autowired
    private MenuItemService menuItemService;



    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemService.menuItemList();
        List<MenuItem> menuItems1 = MenuTreeBuilder.buildTree(menuItems);
        return new ResponseEntity<>(menuItems1, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createMenuItem(@RequestBody MenuItem menuItem) {
        int bool = menuItemService.createMenuItem(menuItem);
        return bool>0 ?ResponseEntity.ok().body("保存菜单成功"):ResponseEntity.status(500).body("保存菜单失败");
    }

    @PutMapping
    public ResponseEntity<String> updateMenuItem(@RequestBody MenuItem menuItem) {
        int bool = menuItemService.updateMenuItem(menuItem);
        return bool>0 ?ResponseEntity.ok().body("编辑菜单成功"):ResponseEntity.status(500).body("编辑菜单失败");
    }

    @GetMapping("/{id}") 
    public ResponseEntity<?> getMenuItemById(@PathVariable String id) {
        List<MenuItem> menuItemById = menuItemService.findMenuItemById(id);
        return new ResponseEntity<>(menuItemById, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {
        Integer bool = menuItemService.deleteMenuItem(id);
        return bool>0 ?ResponseEntity.ok().body("删除菜单成功"):ResponseEntity.status(500).body("删除菜单失败");
    }

    @GetMapping("/supMenu")
    public ResponseEntity<List<MenuItem>> getSupMenu() {
        List<MenuItem> supMenu = menuItemService.findSupMenu();
        return ResponseEntity.ok(supMenu);
    }
}