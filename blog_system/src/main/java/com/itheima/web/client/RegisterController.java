package com.itheima.web.client;

import com.itheima.service.IUserService;
import com.itheima.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private IUserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "comm/register"; // 返回注册页面的视图名称
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      @RequestParam("email") String email,
                                      Model model) {
        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "用户名已存在");
            return "comm/register"; // 返回注册页面视图，并显示错误信息
        }
        // 创建新用户并保存
        User user = new User(username, password, email, true);
        userService.save(user);
        return "redirect:/login"; // 注册成功后重定向到登录页面
    }
}
