package com.exbbs.ex_bbs.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exbbs.ex_bbs.domain.Article;

@Repository
public class ArticleRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);

    /**
     * 全投稿を取得するメソッド
     * @return 全投稿の情報
     */
    public List<Article> findAll() {
        String sql = "SELECT id,name,content FROM articles ORDER BY id DESC";
        List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
        return articleList;
    }

    /**
     * 入力された情報で投稿を作成し、DB上に保存するメソッド
     */
    public void insert(Article article) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);

        if (article.getId() == null) {
            String insertSql = "INSERT INTO articles(name,content) "
                    + " VALUES(:name,:content)";
            template.update(insertSql, param);
        }
    }

    /**
     * IDで指定された投稿とそのコメントを削除するメソッド
     * @param id
     */
    @Transactional
    public void deleteById(int id) {
        String CommentDeleteSql = "DELETE FROM comments WHERE id=:id";
        String ArticleDeleteSql = "DELETE FROM articles WHERE id=:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(CommentDeleteSql, param);
        template.update(ArticleDeleteSql, param);
    }

}
