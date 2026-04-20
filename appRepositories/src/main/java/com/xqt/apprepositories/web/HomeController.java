package com.xqt.apprepositories.web;

import com.xqt.apprepositories.App;
import com.xqt.apprepositories.data.JdbcAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
class HomeController {

    private final JdbcAppRepository appReposity;

    @Autowired
    public HomeController(JdbcAppRepository appReposity) {
        this.appReposity = appReposity;
    }

    @RequestMapping
    public String home() {
        return "home";
    }

    @ModelAttribute
    public void app(Model model) {
        List<App> apps = (List<App>) appReposity.findAll();
        model.addAttribute("apps", apps);
    }
}
