package com.DB.Project.Service;

import com.DB.Project.Entitiy.Doc;
import com.DB.Project.Entitiy.Todo;
import com.DB.Project.Entitiy.User;
import com.DB.Project.Repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocService {

    private final DocRepository docRepository;

    @Autowired
    public DocService(DocRepository docRepository) {
        this.docRepository = docRepository;
    }

    // 특정 Doc 조회
    public Doc getDocById(int docId) {
        return docRepository.getDocById(docId);
    }

    public List<Doc> getHierarchicalDocs(User loginUser) {
        return docRepository.getHierarchicalDocs(loginUser);
    }

    // Doc 생성
    public void createDoc(Doc doc, List<Todo> todos,int parent) {
        docRepository.createDoc(doc,todos,parent);
    }

    // Doc 수정
    public void updateDoc(int docID, Doc doc) {
        docRepository.updateDoc(doc);
    }


    // Doc 삭제
    public void deleteDoc(int docId) {
        docRepository.deleteDoc(docId);
    }
    public List<Doc> getAncestors(int docId) {
        return docRepository.getAncestors(docId);
    }

    public List<Doc> getDescendants(int docId) {
        return docRepository.getDescendants(docId);
    }
}