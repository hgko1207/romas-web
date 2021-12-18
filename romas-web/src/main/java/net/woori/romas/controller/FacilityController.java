package net.woori.romas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import net.woori.romas.domain.FacilityInfo;
import net.woori.romas.domain.db.Reservoir;
import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirMgmt;
import net.woori.romas.domain.db.ReservoirOperation;
import net.woori.romas.domain.param.FacilitySearchParam;
import net.woori.romas.service.ReservoirMgmtService;
import net.woori.romas.service.ReservoirOperationService;
import net.woori.romas.service.ReservoirService;
//import net.woori.romas.service.ReservoirLevelService;
import net.woori.romas.service.common.ChartService;
import net.woori.romas.service.common.ReservoirInfoService;

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
	
	@Autowired
	private ReservoirInfoService reservoirInfoService;
	
	@Autowired
	private ReservoirOperationService reservoirOperationService;
	
	/**
	 * 시설별현황 화면
	 * @param model
	 */
	@GetMapping("")
	public String facility() {
		return "redirect:facility/status";
	}
	
	/**
	 * 시설별현황 화면
	 * @param model
	 */
	@GetMapping("/{facCode}")
	public String facility(Model model, @PathVariable String facCode) {
		
		if (!facCode.equals("status")) {
			Reservoir reservoir = reservoirService.get(facCode);
			if (reservoir != null) {
				model.addAttribute("reservoir", reservoir);
			}
			
			ReservoirMgmt reservoirMgmt = reservoirMgmtService.get(facCode);
			if (reservoirMgmt != null) {
				model.addAttribute("reservoirMgmt", reservoirMgmt);
			} else {
				model.addAttribute("reservoirMgmt", new ReservoirMgmt());
			}
			
			List<ReservoirLevel> reservoirLevels = reservoirInfoService.getReservoirWaterLevel(facCode);
			if (reservoirLevels.size() > 0) {
				model.addAttribute("rate", reservoirLevels.get(0).getRate());
			}
		}
		
		List<String> regionalHeads = reservoirOperationService.getRegionalHeadList();
		model.addAttribute("regionalHeads", regionalHeads);
		
		if (regionalHeads.size() > 0) {
			List<String> branchs = reservoirOperationService.getBranchList(regionalHeads.get(0));
			model.addAttribute("branchs", branchs);
			
			if (branchs.size() > 0) {
				List<ReservoirOperation> facilityNames = reservoirOperationService.getFacilityList(branchs.get(0));
				model.addAttribute("facilitys", facilityNames);
			}
		}
		
		return "facility";
	}
	
	/**
	 * 검색
	 * @param param
	 */
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody FacilitySearchParam param) {
		
		FacilityInfo facilityInfo = new FacilityInfo();
		facilityInfo.setReservoir(reservoirService.get(param.getFacCode()));
		facilityInfo.setReservoirMgmt(reservoirMgmtService.get(param.getFacCode()));
		
		List<ReservoirLevel> reservoirLevels = reservoirInfoService.getReservoirWaterLevel(param.getFacCode());
		if (reservoirLevels.size() > 0) {
			facilityInfo.setRate(reservoirLevels.get(0).getRate());
		}
		
		return new ResponseEntity<>(facilityInfo, HttpStatus.OK);
	}
	
	/**
	 * 차트 검색
	 * @param param
	 */
	@PostMapping("/chart/search")
	public ResponseEntity<?> searchChart(@RequestBody FacilitySearchParam param) {
		
		System.err.println(param);
		
		return new ResponseEntity<>(chartService.createGoogleChartInfo(param), HttpStatus.OK);
	}
}
