package com.DB.Project.Repository;

import com.DB.Project.Entitiy.Doc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
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

    public List<Doc> getAllDocs() {
        String query = "SELECT * FROM Doc";
        return jdbcTemplate.query(query, this::mapRowToDoc);
    }

    public void createDoc(Doc doc) {
        String query = "INSERT INTO Doc (Writer, StartDate, ModDate, Content, Header, UserID) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, doc.getWriter(), doc.getStartDate(), doc.getModDate(), doc.getContent(), doc.getHeader(), doc.getUserId());
    }

    public void updateDoc(Doc doc) {
        String query = "UPDATE Doc SET Writer = ?, StartDate = ?, ModDate = ?, Content = ?, Header = ?, UserID = ? WHERE DocID = ?";
        jdbcTemplate.update(query, doc.getWriter(), doc.getStartDate(), doc.getModDate(), doc.getContent(), doc.getHeader(), doc.getUserId(), doc.getDocId());
    }

    public void deleteDoc(int docId) {
        String query = "DELETE FROM Doc WHERE DocID = ?";
        jdbcTemplate.update(query, docId);
    }

    private Doc mapRowToDoc(ResultSet rs, int rowNum) throws SQLException {
        return new Doc(
                rs.getInt("DocID"),
                rs.getString("Writer"),
                rs.getDate("StartDate"),
                rs.getDate("ModDate"),
                rs.getString("Content"),
                rs.getString("Header"),
                rs.getInt("UserID")
        );
    }
}
