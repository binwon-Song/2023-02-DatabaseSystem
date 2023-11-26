package com.DB.Project.Service;

import com.DB.Project.Entitiy.Doc;
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

    // 모든 Doc 조회
    public List<Doc> getAllDocs() {
        return docRepository.getAllDocs();
    }

    // Doc 생성
    public void createDoc(Doc doc) {
        docRepository.createDoc(doc);
    }

    // Doc 수정
    public void updateDoc(int docID, Doc doc) {
        docRepository.updateDoc(doc);
    }

    // Doc 삭제
    public void deleteDoc(int docId) {
        docRepository.deleteDoc(docId);
    }
}