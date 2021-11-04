package net.woori.romas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 시설별현황 화면 컨트롤러
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("facility")
public class FacilityController {
	
	/**
	 * 시설별현황 화면
	 * @param model
	 */
	@GetMapping("")
	public void facility(Model model) {

	}
	
	/**
	 * 검색
	 * @param model
	 */
	@PostMapping("/search")
	public void search() {

	}
}
