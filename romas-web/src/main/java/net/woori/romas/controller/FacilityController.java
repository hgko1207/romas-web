package net.woori.romas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 시설별현황 화면
 * 
 * @author hgko
 *
 */
@Controller
public class FacilityController {

	@GetMapping("/facility")
	public void facility(Model model) {

	}
}
