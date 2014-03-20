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
public class WelcomeController {

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public String printWelcome(ModelMap map) throws IOException, InterruptedException {
		Article heading = ArticleFactory.getHeading();
		map.addAttribute("headingHeader", heading.getHeader());
		map.addAttribute("headingBody", heading.getBody());

		Article article1 = ArticleFactory.getArticle1();
		map.addAttribute("article1Header", article1.getHeader());
		map.addAttribute("article1Body", article1.getBody());

		Article article2 = ArticleFactory.getArticle2();
		map.addAttribute("article2Header", article2.getHeader());
		map.addAttribute("article2Body", article2.getBody());

		Article article3 = ArticleFactory.getArticle3();
		map.addAttribute("article3Header", article3.getHeader());
		map.addAttribute("article3Body", article3.getBody());
		return "welcome";
	}
}