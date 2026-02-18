package com.exbbs.ex_bbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exbbs.ex_bbs.Repository.ArticleRepository;
import com.exbbs.ex_bbs.Repository.CommentRepository;
import com.exbbs.ex_bbs.domain.Article;
import com.exbbs.ex_bbs.domain.Comment;
import com.exbbs.ex_bbs.form.ArticleForm;

@Controller
@RequestMapping("/exbbs")
public class ExbbsController {

    @Autowired
    private CommentRepository comRepository;
    @Autowired
    private ArticleRepository artRepository;

    @RequestMapping("")
    public String index(Model model) {

        List<Article> articleList = artRepository.findAll();
        
        for(Article article : articleList){
            List<Comment> listComment = comRepository.findByArticleId(article.getId());
            article.setCommentList(listComment);
        }

        model.addAttribute("articleList", articleList);

        return "output";
    }

    @RequestMapping("/post")
    public String postArticles(ArticleForm form) {

        Article article = new Article();
        article.setName(form.getName());
        article.setContent(form.getContent());

        artRepository.insert(article);
        return "redirect:/exbbs";
    }
}
