package com.DB.Project.Controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//lombok dependency추가
@Getter
@Setter
@NoArgsConstructor
@ToString
@Controller
public class UserController { //회원 정보를 필드로 정의
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        System.out.println("Home Page");
        return "index";
    }
}
