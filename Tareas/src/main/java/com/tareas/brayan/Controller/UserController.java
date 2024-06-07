package com.tareas.brayan.Controller;

import com.tareas.brayan.entidades.User;
import com.tareas.brayan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            model.addAttribute("userId", existingUser.getId());
            return "redirect:/tasks?userId=" + existingUser.getId();
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/api/changePassword")
    public String changePasswordForm(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "changePassword";
    }

    @PostMapping("/api/changePassword")
    public String changePassword(@RequestParam("userId") String userId,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Model model) {
        User user = userService.findById(userId);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userService.saveUser(user);
            return "redirect:/tasks?userId=" + userId;
        } else {
            model.addAttribute("error", "Invalid old password");
            return "changePassword";
        }
    }
    @GetMapping("/logout")
	public String cerrar(Model model) {
		return "logout";
	}
    @GetMapping("/help")
   	public String cer(Model model) {
   		return "help";
   	}
    @GetMapping("/about")
   	public String c(Model model) {
   		return "about";
   	}
}

