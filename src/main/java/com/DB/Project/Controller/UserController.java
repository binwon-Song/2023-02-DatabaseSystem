package com.DB.Project.Controller;

import com.DB.Project.Entitiy.User;
import com.DB.Project.Repository.UserRepository;
import com.DB.Project.Service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
@ToString
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, JdbcTemplate jdbcTemplate, UserService userService) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        System.out.println("Home Page");
        model.addAttribute("user", new User());
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String id, @RequestParam String passwd, Model model) {
        if (userService.validateUser(id, passwd)) {
            return "redirect:/docs";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "/";
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/docs";
    }
}