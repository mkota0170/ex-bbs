package com.exbbs.ex_bbs.form;

/**
 * 投稿の入力値を受け取るためのフォーム
 */
public class ArticleForm {
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
        return "ArticleForm [name=" + name + ", content=" + content + "]";
    }

    
}
