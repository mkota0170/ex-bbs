package com.exbbs.ex_bbs.Repository;

// import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
// import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
// import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.exbbs.ex_bbs.domain.Comment;

@Repository
public class CommentRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Comment> COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);

    public List<Comment> findByArticleId(int articleId) {
        String sql = "SELECT id, name,content FROM comments WHERE article_id=:articleId";
        // SqlParameterSource param = new MapSqlParameterSource().addValue("articleId",
        // articleId);
        List<Comment> commentList = template.query(sql, COMMENT_ROW_MAPPER);
        return commentList;
    }

    public void insert(Comment comment) {

    }

    public void deleteByArticleId(int articleId) {

    }
}
