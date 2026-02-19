package com.exbbs.ex_bbs.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.exbbs.ex_bbs.domain.Article;
import com.exbbs.ex_bbs.domain.Comment;

@Repository
public class ArticleRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final ResultSetExtractor<List<Article>> ARTICLE_WITH_COMMENTS_EXTRACTOR =
        rs -> {

            Map<Integer, Article> articleMap = new LinkedHashMap<>();

            while (rs.next()) {

                Integer articleId = rs.getInt("article_id");

                Article article = articleMap.get(articleId);

                if (article == null) {
                    article = new Article();
                    article.setId(articleId);
                    article.setName(rs.getString("article_name"));
                    article.setContent(rs.getString("article_content"));
                    article.setCommentList(new ArrayList<>());

                    articleMap.put(articleId, article);
                }

                Integer commentId = rs.getInt("comment_id");

                if (!rs.wasNull()) {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setName(rs.getString("comment_name"));
                    comment.setContent(rs.getString("comment_content"));

                    article.getCommentList().add(comment);
                }
            }

            return new ArrayList<>(articleMap.values());
        };

    
    /**
     * 全投稿を取得するメソッド
     * 
     * @return 全投稿の情報
     */
    public List<Article> findAll() {
        String sql = "SELECT" + //
                " a.id AS article_id," +
                " a.name AS article_name," +
                " a.content AS article_content," +
                " c.id AS comment_id," +
                " c.name AS comment_name," +
                " c.content AS comment_content" +
                " FROM articles a" +
                " LEFT JOIN comments c" +
                " ON a.id = c.article_id" +
                " ORDER BY a.id DESC, c.id DESC";
        List<Article> articleList = template.query(sql, ARTICLE_WITH_COMMENTS_EXTRACTOR);
        return articleList;
    }

    /**
     * 入力された情報で投稿を作成し、DB上に保存するメソッド
     * 
     * @param article
     */
    public void insert(Article article) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);

        String insertSql = "INSERT INTO articles(name,content) "
                + " VALUES(:name,:content)";
        template.update(insertSql, param);
    }

    /**
     * IDで指定された投稿を削除するメソッド
     * 
     * @param id
     */
    public void deleteById(int id) {
        final String articleDeleteSql = "DELETE FROM articles WHERE id=:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(articleDeleteSql, param);
    }
}
