package com.group8.library_management.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Trang login admin
    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "login"; // trả về view login.html
    }
}
