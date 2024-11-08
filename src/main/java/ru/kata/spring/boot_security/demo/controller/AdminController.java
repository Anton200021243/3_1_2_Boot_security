package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "show";
    }

    @GetMapping("/user")
    public String showUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "index";   //admin/user?id=69
    }

    @GetMapping("/new")
    public String addNewAdmin(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        // Здесь roleService должен быть вашим сервисом, предоставляющим все роли
        return "new";
    }

    @PostMapping("/new")
    public String createNewAdmin(@ModelAttribute("user") User user, @ModelAttribute("role") Role role) {
        userService.updateUser(user);
        return "index";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") int id, @ModelAttribute("role") Role role) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("id") int id, @ModelAttribute("role") Role role) {
        userService.updateUser(user);
        return "redirect:";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userService.deleteUser(id);
        return "redirect:";
    }
}
