package net.woori.romas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.woori.romas.domain.param.SearchParam;

/**
 * 관리자 화면
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("admin")
public class AdminController {

	@GetMapping("")
	public void admin(Model model) {

	}
	
	@PostMapping("/search")
	@ResponseBody
	public void search(@RequestBody SearchParam param) {

	}
}
