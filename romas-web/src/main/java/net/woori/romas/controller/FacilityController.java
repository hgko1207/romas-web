package net.woori.romas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import net.woori.romas.domain.param.SearchParam;

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
	 * @param param
	 */
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody SearchParam param) {
		
		System.err.println(param);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
