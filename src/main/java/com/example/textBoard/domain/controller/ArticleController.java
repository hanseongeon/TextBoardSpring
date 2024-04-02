package com.example.textBoard.domain.controller;

import com.example.textBoard.base.CommonUtil;
import com.example.textBoard.domain.model.*;
import com.example.textBoard.domain.view.ArticleView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Model - Controller - View
@Controller
public class ArticleController { // Model + Controller

    CommonUtil commonUtil = new CommonUtil();
    Repository articleRepository = new ArticleMySQLRepository();
    @RequestMapping("/search")
    public String search(@RequestParam(value = "keyword", defaultValue = "") String keyword,Model model) {

        ArrayList<Article> searchedList = articleRepository.findArticleByKeyword(keyword);
        model.addAttribute("searchedList",searchedList);
        return "searchList";
    }

    @RequestMapping("/detail/{num}")
    public String detail(@PathVariable("num") int num, Model model) {

        Article article = articleRepository.findArticleById(num);

        if (article == null) {
            return "없는 게시물 입니다.";
        }

        article.increaseHit();
        articleRepository.hitSave(article);
        model.addAttribute("article",article);

        return "detail";

    }

    @RequestMapping("/delete/{num}")
    public String delete(@PathVariable("num") int num) {

        Article article = articleRepository.findArticleById(num);

        if (article == null) {
            return "없는 게시물입니다.";
        }

        articleRepository.deleteArticle(article);
//        System.out.printf("%d 게시물이 삭제되었습니다.\n", inputId);
        return "redirect:/list";
    }

    @GetMapping("/update/{num}")
    public String update(@PathVariable("num") int num,Model model) {

        Article article = articleRepository.findArticleById(num);

        if (article == null) {
            throw new RuntimeException("없는 게시물 입니다.");
        }

        model.addAttribute("article",article);
        return "updateForm";

    }

    @PostMapping("/update/{num}")
    public String update(@PathVariable("num") int num,
                         @RequestParam("title") String title,
                         @RequestParam("body") String body){

        Article article = articleRepository.findArticleById(num);

        if (article == null) {
            throw new RuntimeException("없는 게시물 입니다.");
        }

        articleRepository.updateArticle(article,title,body);
        return "redirect:/detail/%d".formatted(num);
    }

    @RequestMapping("/list")
    public String list(Model model) {
        ArrayList<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList",articleList);

        return "list";
    }

    //실제 데이터 저장 처리 부분
    @PostMapping("/add")
    public String add(@RequestParam("title") String title, @RequestParam("body") String body, Model model) {

        articleRepository.saveArticle(title, body);

        // 문제 원인 : add 요청의 결과 화면을 list로 보여주고 있다.
        // 문제 해결 : add url을 list로 바꾸면 된다
        // controller 에서 주소를 바꾸는 법 : redirect
        return "redirect:/list"; //브라우저의 주소가 /list로 바뀜
    }

    //입력화면 보여주기
    @GetMapping("/add")
    public String form(){
        return "form";
    }


}