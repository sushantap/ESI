package com.talentica.esi.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.talentica.esi.test.Article;
import com.talentica.esi.test.ArticleFactory;

@Controller
@RequestMapping("/")
public class NewsController {

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public String printWelcome() throws IOException, InterruptedException {
		return "news";
	}
	
	@RequestMapping(value = "/heading" , method = RequestMethod.GET)
	public String heading(ModelMap map) throws IOException, InterruptedException {
		Article heading = ArticleFactory.getHeading();
		map.addAttribute("heading", heading.getHeader());
		map.addAttribute("body", heading.getBody());
		return "heading";
	}
	
	@RequestMapping(value = "/article1" , method = RequestMethod.GET)
	public String article1(ModelMap map) throws IOException, InterruptedException {
		Article article = ArticleFactory.getArticle1();
		map.addAttribute("heading", article.getHeader());
		map.addAttribute("body", article.getBody());
		return "article";
	}
	
	@RequestMapping(value = "/article2" , method = RequestMethod.GET)
	public String article2(ModelMap map) throws IOException, InterruptedException {
		Article article = ArticleFactory.getArticle2();
		map.addAttribute("heading", article.getHeader());
		map.addAttribute("body", article.getBody());
		return "article";
	}
	
	@RequestMapping(value = "/article3" , method = RequestMethod.GET)
	public String article3(ModelMap map) throws IOException, InterruptedException {
		Article article = ArticleFactory.getArticle3();
		map.addAttribute("heading", article.getHeader());
		map.addAttribute("body", article.getBody());
		return "article";
	}
}