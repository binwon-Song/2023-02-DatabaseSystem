package com.DB.Project.Controller;

import com.DB.Project.Entitiy.*;
import com.DB.Project.MyConfig.SessionConst;
import com.DB.Project.Service.DocService;
import com.DB.Project.Service.ShareService;
import com.DB.Project.Service.TodoService;
import com.DB.Project.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ShareController {
    private final ShareService shareService;
    private  final UserService userService;
    private  final DocService docService;
    private  final TodoService todoService;
    @Autowired
    public ShareController(ShareService shareService, UserService userService, DocService docService, TodoService todoService) {
        this.shareService = shareService;
        this.userService=userService;
        this.docService=docService;
        this.todoService=todoService;
    }


    @PostMapping("/shares/{docId}")
    public String addShareUser(HttpServletRequest request, @PathVariable("docId") int docId,ShareUser shareUser) {
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);

        System.out.println(shareUser.getShareId());
        User user = userService.getUserByID(shareUser.getShareId());

        // shareService.ShareAdd 메서드의 파라미터 순서를 변경하였습니다.
        shareService.ShareAdd(user.getUserID(), user.getId(),user.getName(), shareUser.getRole(), docId);
        return "redirect:/docs";
    }

    @GetMapping("/shares")
    public String getShareDoc(HttpServletRequest request,Model model){
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);
        List<Doc> records= shareService.getSahreDoc(loginMember.getUserID());
        model.addAttribute("user",loginMember);
        model.addAttribute("records",records);
        return "ShareDoc";
    }
    @GetMapping("/shares/{docID}")
    public String detailDoc(@PathVariable("docID") int docID, @ModelAttribute Doc detailDoc, Model model){
        System.out.println("Detail Page");
        Doc doc=docService.getDocById(docID);
        List<Todo> todo= todoService.getTodoByDocId(docID);
        ShareUser shareUser=shareService.getShareUser(docID);
        DocWithTodo docTodo=new DocWithTodo(doc,todo);
        model.addAttribute("records",docTodo);
        model.addAttribute("shareUser",shareUser);
        return "/SharedDocDetail"; // Redirect to the list of documents
    }
}
