package com.DB.Project.Controller;

import com.DB.Project.Entitiy.User;
import com.DB.Project.MyConfig.SessionConst;
import com.DB.Project.Repository.UserRepository;
import com.DB.Project.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Getter
@Setter
@ToString
@Controller
@SessionAttributes("loginUser")
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
    public String login(@RequestParam String id, @RequestParam String passwd, Model model, HttpServletRequest httpServletRequest) {
        // Session이 있으면 가져오고 없으면 Session을 생성해서 return (default = true)
        HttpSession session = httpServletRequest.getSession(false);
        if (session ==null){
            session = httpServletRequest.getSession(true);
        }
        if (userService.validateUser(id, passwd)) {
            User loginUser = userService.getUserByID(id);
            session.setAttribute(SessionConst.loginUser,loginUser);
            session.setMaxInactiveInterval(1800);
            return "redirect:/docs";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "/";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, SessionStatus sessionStatus) {
        // Check if the login user attribute is set
        HttpSession session = request.getSession(false);

        if (session != null) {
            User loginMember = (User) session.getAttribute(SessionConst.loginUser);

            if (loginMember != null) {
                System.out.println("loginMember logout: " + loginMember.getName());
            }

            // Set the session processing as complete
            sessionStatus.setComplete();

            // Now trying to access loginMember will result in NullPointerException
            // System.out.println("loginMember logout: " + loginMember.getName());
        }

        return "redirect:/";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute User user,Model model) {
        userService.createUser(user);
        // 로그인 작업 추가
        User loginUser = userService.getUserByID(user.getId());
        model.addAttribute("loginUser", loginUser);
        return "redirect:/";
    }
}