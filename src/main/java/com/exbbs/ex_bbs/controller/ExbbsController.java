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
import com.exbbs.ex_bbs.form.CommentForm;

@Controller
@RequestMapping("/exbbs")
public class ExbbsController {

    @Autowired
    private CommentRepository comRepository;
    @Autowired
    private ArticleRepository artRepository;

    /**
     * 投稿と、その投稿に紐づくコメントを出力するメソッド
     * 
     * @param model
     * @return 全投稿とコメント
     */
    @RequestMapping("")
    public String index(Model model) {

        List<Article> articleList = artRepository.findAll();

        for (Article article : articleList) {
            List<Comment> listComment = comRepository.findByArticleId(article.getId());
            article.setCommentList(listComment);
        }

        model.addAttribute("articleList", articleList);

        return "output";
    }

    /**
     * 入力した内容の投稿をDBに保存し、再度入力画面に遷移するメソッド
     * 
     * @param form
     * @return 入力画面に遷移
     */
    @RequestMapping("/post")
    public String insertArticles(ArticleForm form) {

        Article article = new Article();
        article.setName(form.getName());
        article.setContent(form.getContent());

        artRepository.insert(article);
        return "redirect:/exbbs";
    }

    /**
     * 特定の投稿に対してのコメントをDBに保存し、再度入力画面に遷移するメソッド
     * 
     * @param form
     * @return 入力画面に遷移
     */
    @RequestMapping("/comment")
    public String insertComment(CommentForm form) {

        Comment comment = new Comment();
        comment.setName(form.getName());
        comment.setContent(form.getContent());
        comment.setArticleId(form.getArticleId());

        comRepository.insert(comment);
        return "redirect:/exbbs";
    }

    /**
     * 選択された投稿と、その投稿についたコメントを削除するメソッド
     * 
     * @param id
     * @return 入力画面に遷移
     */
    @RequestMapping("/delete")
    public String deleteArticle(int id) {
        comRepository.deleteByArticleId(id);
        artRepository.deleteById(id);
        return "redirect:/exbbs";
    }
}
