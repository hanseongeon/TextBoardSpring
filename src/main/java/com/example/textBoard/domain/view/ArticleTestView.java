package com.example.textBoard.domain.view;


import com.example.textBoard.domain.model.Article;

import java.util.ArrayList;

public class ArticleTestView {

    public void printArticleList(ArrayList<Article> targetList) {

        System.out.println("===================");
        for (int i = 0; i < targetList.size(); i++) {

            Article article = targetList.get(i);

            System.out.println("번호 : " + article.getId());
            System.out.printf("제목 : %s\n", article.getTitle());
            System.out.println("===================");
        }
    }
    public void printArticleDetail(Article article) {
        System.out.println("===================");
        System.out.println("번호 : " + article.getId());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
        System.out.println("등록날짜 : " + article.getRegDate());
        System.out.println("조회수 : " + article.getHit());
        System.out.println("===================");
    }
}