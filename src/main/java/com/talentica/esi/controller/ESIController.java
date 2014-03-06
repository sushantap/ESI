package com.talentica.esi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/*")
public class ESIController {
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(HttpServletResponse response) throws IOException, InterruptedException {
		return "";
	}
}