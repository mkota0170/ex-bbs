package com.exbbs.ex_bbs.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.exbbs.ex_bbs.domain.Comment;

@Repository
public class commentRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Comment> COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);

    /**
     * 投稿に紐づいたコメントを取得するメソッド
     * 
     * @param articleId
     * @return 投稿IDに紐づいた全コメント
     */
    public List<Comment> findByArticleId(int articleId) {
        String sql = "SELECT id, name,content, article_id FROM comments WHERE article_id=:articleId ORDER BY id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
        List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
        return commentList;
    }

    /**
     * 投稿に対してコメントを作成し、DBに保存するメソッド
     * 
     * @param comment
     */
    public void insert(Comment comment) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(comment);

        String insertSql = "INSERT INTO comments(name,content,article_id ) "
                + " VALUES(:name,:content,:articleId)";
        template.update(insertSql, param);
    }

    /**
     * 投稿に紐づいたコメントを削除するメソッド
     * @param id
     */
    public void deleteByArticleId(int id){
        final String articleDeleteSql = "DELETE FROM articles WHERE id=:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(articleDeleteSql, param);
    }
}
