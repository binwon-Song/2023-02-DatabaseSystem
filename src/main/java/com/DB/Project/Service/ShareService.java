package com.DB.Project.Service;

import com.DB.Project.Entitiy.Doc;
import com.DB.Project.Repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareService {
    private final ShareRepository shareRepository;

    @Autowired
    public ShareService(ShareRepository shareRepository){
        this.shareRepository=shareRepository;
    }

    public void ShareAdd(Integer userId,String id,String name,String role,Integer docid){
        shareRepository.ShareAdd(userId,id,name,role,docid);
    }

    public List<Doc> getSahreDoc(Integer userid)
    {
        return shareRepository.getShareDoc(userid);
    }
}
