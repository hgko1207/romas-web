package net.woori.romas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 메인 화면
 * 
 * @author hgko
 *
 */
@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "redirect:home";
	}
	
	@GetMapping("/home")
	public void home(Model model) {

	}
}
