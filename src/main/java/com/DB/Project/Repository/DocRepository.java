package com.DB.Project.Repository;

import com.DB.Project.Entitiy.Doc;
import com.DB.Project.Entitiy.Todo;
import com.DB.Project.Entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@SessionAttributes("loginUser")
public class DocRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DocRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Doc getDocById(int docId) {
        String query = "SELECT * FROM Doc WHERE DocID = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{docId}, this::mapRowToDoc);
    }

    public List<Doc> getHierarchicalDocs(User loginUser) {

        String query = "SELECT D.* FROM Doc D " +
                "JOIN ClouserTable C ON D.DocID = C.DescendantDocID " +
                "WHERE D.UserID = ? AND C.AncestorDocID=? AND C.Depth=1";

        Integer userid=loginUser.getUserID();

        return jdbcTemplate.query(query, new Object[]{userid,userid}, this::mapRowToDoc);
    }


    @Transactional
    public void createDoc(Doc doc, List<Todo> todos,int parent) {
        String query = "INSERT INTO Doc (Writer, ModDate, Content, Header, UserID) VALUES (?, ?, ?, ?, ?)";
        String todoQuery = "INSERT INTO TODOList (Prior, Header, DocID) VALUES (?, ?, ?)";
        String closureQuery = "" +
                "INSERT INTO ClouserTable (AncestorDocID, DescendantDocID,Depth) " +
                "SELECT C.AncestorDocID, ?,C.Depth+1 " +
                "FROM ClouserTable AS C " +
                "WHERE C.DescendantDocID=? " +
                "UNION ALL " +
                "SELECT ?,?,0"; // insert num, parent, insert, insert


        // Execute the insert query
        Integer userid=doc.getUserId();
        jdbcTemplate.update(query, doc.getWriter(), doc.getModDate(), doc.getContent(), doc.getHeader(), doc.getUserId());
        int docId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        // Set the DocID in the Doc object
        doc.setDocId(docId);
        // Insert TODOs with the retrieved DocID
        if (todos != null) {
            for (Todo todo : todos) {
                jdbcTemplate.update(todoQuery, todo.getPriority(), todo.getHeader(), docId);
            }
        }

        jdbcTemplate.update(closureQuery, docId, parent, docId,docId);
    }


    public void updateDoc(Doc doc) {
        String query = "UPDATE Doc SET Writer = ?, StartDate = ?, ModDate = ?, Content = ?, Header = ?, UserID = ? WHERE DocID = ?";
        jdbcTemplate.update(query, doc.getWriter(), doc.getModDate(), doc.getContent(), doc.getHeader(), doc.getUserId(), doc.getDocId());
    }

    public void deleteDoc(int docId) {
        String query = "DELETE FROM Doc WHERE DocID = ?";
        jdbcTemplate.update(query, docId);
    }
    // 특정 문서의 조상 문서 가져오기
    public List<Doc> getAncestors(int docId) {
        String query = "SELECT d.* FROM Doc d " +
                "JOIN ClouserTable ct ON d.DocID = ct.AncestorDocID " +
                "WHERE ct.DescendantDocID = ?";

        return jdbcTemplate.query(query, new Object[]{docId}, this::mapRowToDoc);
    }

    // 특정 문서의 자손 문서 가져오기
    public List<Doc> getDescendants(int docId) {
        String query = "SELECT d.* FROM Doc d " +
                "JOIN ClouserTable C ON d.DocID = C.DescendantDocID " +
                "WHERE C.AncestorDocID = ? AND d.DocID <> ?";

        return jdbcTemplate.query(query, new Object[]{docId,docId}, this::mapRowToDoc);
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
