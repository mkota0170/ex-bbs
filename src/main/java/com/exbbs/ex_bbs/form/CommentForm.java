package com.exbbs.ex_bbs.form;

/**
 * コメントの入力値を受け取るためのフォーム
 */
public class CommentForm {
    private Integer articleId;
    private String name;
    private String content;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "CommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
    }
    public Integer getArticleId() {
        return articleId;
    }
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
}
