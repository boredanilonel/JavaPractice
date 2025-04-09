package com.example.shop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit")
    public String editData() {
        return "Data edited by admin.";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/view")
    public String viewData() {
        return "Data viewed.";
    }
}
