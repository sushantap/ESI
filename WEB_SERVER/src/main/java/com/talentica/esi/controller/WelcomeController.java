package com.talentica.esi.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class WelcomeController {

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String printWelcome() throws IOException, InterruptedException {
		Thread.sleep(2000);
		return "welcome";
	}
	
	@RequestMapping(value = "/heading" , method = RequestMethod.GET)
	public String heading() throws IOException, InterruptedException {
		Thread.sleep(1000);
		return "heading";
	}
	
	@RequestMapping(value = "/article1" , method = RequestMethod.GET)
	public String article1() throws IOException, InterruptedException {
		Thread.sleep(5000);
		return "article1";
	}
	
	@RequestMapping(value = "/article2" , method = RequestMethod.GET)
	public String article2() throws IOException, InterruptedException {
		Thread.sleep(1000);
		return "article2";
	}
	
	@RequestMapping(value = "/article3" , method = RequestMethod.GET)
	public String article3() throws IOException, InterruptedException {
		Thread.sleep(10000);
		return "article3";
	}
}