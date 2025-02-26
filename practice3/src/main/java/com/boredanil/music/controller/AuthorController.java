// Контроллер, который возвращает страницу с информацией об авторе
package com.boredanil.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorController {

    @GetMapping("/author")
    public String authorPage(Model model) {
        model.addAttribute("name", "Данил");
        model.addAttribute("description", "ИП-217, 20 лет, СибГУТИ 3 курс, не люблю холодец.");
        return "author";
    }
}
