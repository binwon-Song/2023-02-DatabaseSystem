package com.DB.Project.Controller;

import com.DB.Project.Service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.DB.Project.Entitiy.Doc;
import java.util.List;


@Controller
@RequestMapping("/docs")
public class DocController {

    private final DocService docService;

    @Autowired
    public DocController(DocService docService) {
        this.docService = docService;
    }

    @GetMapping
    public String getAllDocs(Model model) {
        List<Doc> docs = docService.getAllDocs();
        model.addAttribute("docs", docs);
        return "DocList";
    }

    @PostMapping
    public String addDoc() {
        System.out.println("Add Doc ");
        // Implement the logic to add a new document
        return "redirect:/docs"; // Redirect to the list of documents
    }

    @DeleteMapping("/{docID}")
    public String deleteDoc(@PathVariable int docID) {
        docService.deleteDoc(docID);
        System.out.println("Delete Doc ");
        return "redirect:/docs"; // Redirect to the list of documents
    }

    @PutMapping("/{docID}")
    public String updateDoc(@PathVariable int docID, @ModelAttribute Doc updateDoc) {
        System.out.println("Update Doc");
        docService.updateDoc(docID, updateDoc);
        return "redirect:/docs"; // Redirect to the list of documents
    }
}