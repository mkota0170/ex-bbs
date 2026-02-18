package com.exbbs.ex_bbs.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exbbs.ex_bbs.domain.Article;

@Repository
public class ArticleRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    public List<Article> findAll(){
        return null;
    }

    public void insert(Article article){
        
    }

    public void deleteById(int id){
        
    }
    
}
