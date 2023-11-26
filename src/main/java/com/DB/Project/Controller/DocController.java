package com.DB.Project.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DocController {

    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    public String home() {
        System.out.println("After Login ");
        return "DocList";
    }
    @RequestMapping(value = "/docs", method = RequestMethod.POST)
    public String addDoc() {
        System.out.println("Add Login ");
        return "DocList";
    }
    @RequestMapping(value = "/docs", method = RequestMethod.DELETE)
    public String deleteDoc() {
        System.out.println("Delete Login ");
        return "DocList";
    }
    @RequestMapping(value = "/docs", method = RequestMethod.PUT)
    public String updateDoc() {
        System.out.println("Update Login ");
        return "DocList";
    }
}
