package com.DB.Project.Repository;

import com.DB.Project.Entitiy.Doc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ShareRepository {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ShareRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate=jdbcTemplate;
    }


    public void ShareAdd(Integer userId,String id,String name,String role,Integer docid){
        String query="INSERT INTO ShareUser(ShareUserID,ShareID,ShareName,Role,DocID) VALUES(?,?,?,?,?)";
        jdbcTemplate.update(query,userId,id,name,role,docid);
    }
    public List<Doc> getShareDoc(Integer userid){
        String query="SELECT D.* FROM Doc D JOIN ShareUser SU ON SU.DocID=D.DocID WHERE SU.ShareUserID=?";
        return jdbcTemplate.query(query, new Object[]{userid}, this::mapRowToDoc);

    }

    private Doc mapRowToDoc(ResultSet rs, int rowNum) throws SQLException {
        Doc doc = new Doc(
                rs.getInt("DocID"),
                rs.getString("Writer"),
                rs.getString("ModDate"),
                rs.getString("Content"),
                rs.getString("Header"),
                rs.getInt("UserID")
        );
        return doc;
    }


}
