package kh.mclass.crawling.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kh.mclass.crawling.Run;
import kh.mclass.crawling.domain.CrawlingEntity;

@Controller
public class CrawlingController {
	@Autowired
	private Run run;
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/list")
	public String list(Model model) throws Exception {
		List<CrawlingEntity> data = run.crawling(); 
		model.addAttribute("data", data);
		System.out.println("Controller Title : " + data);
		return "list";
	}
	

	
	
}
