package com.DB.Project.Controller;

import com.DB.Project.Entitiy.Doc;
import com.DB.Project.Entitiy.DocWithTodo;
import com.DB.Project.Entitiy.Todo;
import com.DB.Project.Entitiy.User;
import com.DB.Project.MyConfig.SessionConst;
import com.DB.Project.Service.DocService;
import com.DB.Project.Service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("/docs")
public class DocController {

    private final DocService docService;
    private final TodoService todoService;

    @Autowired
    public DocController(DocService docService, TodoService todoService) {
        this.docService = docService;
        this.todoService = todoService;
    }

    @GetMapping
    public String getAllDocs(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        User loginMember=(User)session.getAttribute(SessionConst.loginUser);
        List<Doc> hierarchicalDocs = docService.getHierarchicalDocs(loginMember);
        model.addAttribute("records", hierarchicalDocs);
        model.addAttribute("user",loginMember);
        return "DocList";
    }

    @GetMapping("/add")
    public String addDoc(Model model) {
        model.addAttribute("docWithTodo",new DocWithTodo());
        return "/DocAdd";
    }

    // 기록 추가 폼에서 데이터를 받아 기록 추가
    @PostMapping("/add")
    public String addDoc(@ModelAttribute("docWithTodo") DocWithTodo docWithTodo, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Todo> todos = docWithTodo.getTodos();

        Doc doc=docWithTodo.getDoc();
        doc.setWriter(loginMember.getName());
        doc.setModDate(formattedDate);
        doc.setUserId(loginMember.getUserID());
        docService.createDoc(doc, todos, loginMember.getUserID());
        return "redirect:/docs";
    }

    @DeleteMapping("/{docID}")
    @ResponseBody
    public String deleteDoc(@PathVariable int docID) {
        docService.deleteDoc(docID);
        System.out.println("Delete Doc ");
        return "redirect:/docs"; // Redirect to the list of documents
    }


    @PutMapping("/{docID}")
    public String updateDoc(@PathVariable int docID, @ModelAttribute DocWithTodo docWithTodo, HttpServletRequest request) {
        System.out.println("Update Doc");
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Todo> todos = docWithTodo.getTodos();

        Doc doc=docWithTodo.getDoc();
        doc.setWriter(loginMember.getName());
        doc.setModDate(formattedDate);
        doc.setUserId(loginMember.getUserID());

        docService.createDoc(doc, todos, loginMember.getUserID());
        docService.updateDoc(doc,todos);
        return "redirect:/docs"; // Redirect to the list of documents
    }

    @GetMapping("/{docID}")
    public String detailDoc(@PathVariable("docID") int docID,@ModelAttribute Doc detailDoc,Model model){
        System.out.println("Detail Page");
        Doc doc=docService.getDocById(docID);
        List<Todo> todo= todoService.getTodoByDocId(docID);
        DocWithTodo docTodo=new DocWithTodo(doc,todo);
        model.addAttribute("records",docTodo);
        return "/DocDetail"; // Redirect to the list of documents
    }
    @GetMapping("/mod/{docID}")
    public String modDoc(@PathVariable("docID") int docID,Model model,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);
        Doc doc=docService.getDocById(docID);
        List<Todo> todo= todoService.getTodoByDocId(docID);
        DocWithTodo docTodo=new DocWithTodo(doc,todo);

        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        model.addAttribute("records",docTodo);
        doc.setWriter(loginMember.getName());
        doc.setModDate(formattedDate);
        doc.setUserId(loginMember.getUserID());
        return "DocMod";
    }

    @GetMapping(value = "/{docId}/descendants", produces = "application/json")
    @ResponseBody
    public List<Doc> getDescendants(@PathVariable("docId") int docId,Model model) {
        List<Doc> child=docService.getDescendants(docId);
        model.addAttribute("subRecords",child);
        return child;
    }

    @GetMapping("/{docId}/add")
    public String addChildDoc(@PathVariable int docId,Model model) {
        Doc doc=docService.getDocById(docId);
        model.addAttribute("docId",doc.getDocId());
        // Implement the logic to add a new document
        return "/DocChildAdd"; // Redirect to the list of documents
    }

    // 기록 추가 폼에서 데이터를 받아 기록 추가
    @PostMapping("/{docId}/add")
    public String addChildDoc(@PathVariable("docId") int parent,@ModelAttribute("docWithTodo") DocWithTodo docWithTodo, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Todo> todos = docWithTodo.getTodos();

        Doc doc=docWithTodo.getDoc();
        doc.setWriter(loginMember.getName());
        doc.setModDate(formattedDate);
        doc.setUserId(loginMember.getUserID());
        docService.createDoc(doc, todos,parent);
        return "redirect:/docs";
    }

    @GetMapping("/mark")
    public String getMarkDoc(HttpServletRequest request,Model model)
    {
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);

        List<Doc> markDoc=docService.getBookmark(loginMember.getUserID());

        model.addAttribute("records",markDoc);
        model.addAttribute("user",loginMember);
        return "DocMark";
    }

    @PostMapping("/{docId}/mark")
    @ResponseBody
    public void markDoc(@PathVariable("docId") int docId,HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);
        docService.markDoc(loginMember.getUserID(), docId);
    }
    @DeleteMapping("/mark/{docId}")
    @ResponseBody
    public void deleteMarkDoc(@PathVariable("docId") Integer docId,HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);
        docService.deleteMark(loginMember.getUserID(),docId);
    }

    @GetMapping("/todos")
    public String getTodos(HttpServletRequest request,Model model)
    {
        HttpSession session = request.getSession(false);
        User loginMember = (User) session.getAttribute(SessionConst.loginUser);
        List<Todo> todos=todoService.getTodoByUserId(loginMember.getUserID());
        model.addAttribute("todos",todos);
        return "TodoList";

    }



}