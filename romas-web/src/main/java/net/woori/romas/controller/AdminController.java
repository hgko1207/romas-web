package net.woori.romas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 관리자 화면
 * 
 * @author hgko
 *
 */
@Controller
public class AdminController {

	@GetMapping("/admin")
	public void admin(Model model) {

	}
}
