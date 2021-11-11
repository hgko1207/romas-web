package net.woori.romas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import net.woori.romas.domain.db.Reservoir;
import net.woori.romas.domain.db.ReservoirMgmt;
import net.woori.romas.domain.param.SearchParam;
import net.woori.romas.service.ReservoirMgmtService;
import net.woori.romas.service.ReservoirService;
import net.woori.romas.service.common.ChartService;

/**
 * 시설별현황 화면 컨트롤러
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("facility")
public class FacilityController {
	
	@Autowired
	private ChartService chartService;
	
	@Autowired
	private ReservoirService reservoirService;
	
	@Autowired
	private ReservoirMgmtService reservoirMgmtService;
	
	/**
	 * 시설별현황 화면
	 * @param model
	 */
	@GetMapping("")
	public void facility(Model model) {
		
		String facCode = "2671010056";
		
		Reservoir reservoir = reservoirService.get(facCode);
		if (reservoir != null) {
			model.addAttribute("reservoir", reservoir);
		}
		
		ReservoirMgmt reservoirMgmt =  reservoirMgmtService.get(facCode);
		if (reservoirMgmt != null) {
			model.addAttribute("reservoirMgmt", reservoirMgmt);
		}
	}
	
	/**
	 * 검색
	 * @param param
	 */
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody SearchParam param) {
		
		System.err.println(param);
		
		return new ResponseEntity<>(chartService.createGoogleChartInfo(), HttpStatus.OK);
	}
}
