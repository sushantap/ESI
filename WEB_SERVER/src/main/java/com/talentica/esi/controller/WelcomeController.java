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
}