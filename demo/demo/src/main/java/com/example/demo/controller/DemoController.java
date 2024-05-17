package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
	
	@GetMapping("/main")
	public String mainMethod(Model model) {
		System.out.println("aaa");
		return "aaa";
	}
}
