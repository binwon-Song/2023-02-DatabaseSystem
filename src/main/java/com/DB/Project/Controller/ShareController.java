package com.DB.Project.Controller;

import com.DB.Project.Entitiy.Doc;
import com.DB.Project.Entitiy.ShareUser;
import com.DB.Project.Entitiy.User;
import com.DB.Project.MyConfig.SessionConst;
import com.DB.Project.Service.ShareService;
import com.DB.Project.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shares")
public class ShareController {
    private final ShareService shareService;
    private  final UserService userService;
    @Autowired
    public ShareController(ShareService shareService,UserService userService) {
        this.shareService = shareService;
        this.userService=userService;
    }


    @PostMapping("/{docId}")
    public String addShareUser(HttpServletRequest request, @PathVariable("docId") int docid,ShareUser shareUser) {
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);

        System.out.println(shareUser.getShareId());
        User user = userService.getUserByID(shareUser.getShareId());

        // shareService.ShareAdd 메서드의 파라미터 순서를 변경하였습니다.
        shareService.ShareAdd(user.getUserID(), user.getId(),user.getName(), shareUser.getRole(), docid);
        return "redirect:/docs";
    }

    @GetMapping("/")
    public String getShareDoc(HttpServletRequest request,Model model){
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);
        List<Doc> records= shareService.getSahreDoc(loginMember.getUserID());
        model.addAttribute("records",records);
        return "ShareDoc";
    }
}
