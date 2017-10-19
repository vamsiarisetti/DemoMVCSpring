package org.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
	final static Logger logger = Logger.getLogger(HelloWorldController.class);

	@RequestMapping("/hello")
	public ModelAndView showMessage(@RequestParam(value = "name", required = false, defaultValue = "World") String name, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("in controller");
		ModelAndView mv = new ModelAndView("welcome");
		mv.addObject("message", "Hello "+name+", Welcome to Spring MVC!");
		mv.addObject("name", name);

		request.setAttribute("UserName", name);
		return mv;
	}
}