package net.woori.romas.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.woori.romas.domain.ReservoirInfo;
import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirMgmt;
import net.woori.romas.domain.db.ReservoirOperation;
import net.woori.romas.domain.param.AdminSearchParam;
import net.woori.romas.service.ReservoirMgmtService;
import net.woori.romas.service.ReservoirOperationService;
import net.woori.romas.service.ReservoirService;
import net.woori.romas.service.common.ReservoirInfoService;

/**
 * 관리자 화면
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private ReservoirService reservoirService;
	
	@Autowired
	private ReservoirMgmtService reservoirMgmtService;
	
	@Autowired
	private ReservoirOperationService reservoirOperationService;
	
	@Autowired
	private ReservoirInfoService reservoirInfoService;

	/**
	 * 관리자 화면
	 * @param model
	 */
	@GetMapping("")
	public String admin(Model model) {
		
		List<Integer> months = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			months.add(i);
		}
		
		List<String> emls = Arrays.asList("1초순", "2중순", "3하순");
		
		model.addAttribute("months", months);
		model.addAttribute("emls", emls);
		
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
		
		return "admin";
	}
	
	/**
	 * 저수지 1년 정보 조회
	 * @param param
	 * @return
	 */
	@PostMapping("/search")
	@ResponseBody
	public ResponseEntity<?> search(@RequestBody AdminSearchParam param) {
		
		List<ReservoirOperation> operations = reservoirOperationService.getList(param).stream().map(data -> {
			List<ReservoirLevel> reservoirLevels = reservoirInfoService.getReservoirWaterLevel(data.getFacCode());
			if (reservoirLevels.size() > 0) {
				data.setCurrentWaterLevel(reservoirLevels.get(0).getWaterLevel());
			}
			return data;
		}).collect(Collectors.toList());
		
		return new ResponseEntity<>(operations, HttpStatus.OK);
	}
	
	/**
	 * 지사 정보 조회
	 * @param regionalHead
	 * @return
	 */
	@GetMapping("/branch/list")
	@ResponseBody
	public ResponseEntity<?> getBranchList(String regionalHead) {
		
		return new ResponseEntity<>(reservoirOperationService.getBranchList(regionalHead), HttpStatus.OK);
	}
	
	/**
	 * 지점 정보 조회
	 * @param regionalHead
	 * @return
	 */
	@GetMapping("/facility/list")
	@ResponseBody
	public ResponseEntity<?> getFacilityList(String branch) {
		
		return new ResponseEntity<>(reservoirOperationService.getFacilityList(branch), HttpStatus.OK);
	}
	
	/**
	 * 저수지 정보 등록
	 * @return
	 */
	@PostMapping("reservoir")
	@ResponseBody
	public ResponseEntity<?> registReservoir(ReservoirInfo reservoirInfo) {
		System.err.println(reservoirInfo);
		
		boolean result = false;
		
		if (reservoirService.get(reservoirInfo.getFacCode()) != null) {
			return new ResponseEntity<>("등록된 시설입니다.", HttpStatus.BAD_REQUEST);
		}
		
//		new Reservoir(reservoirInfo);
		result = reservoirMgmtService.regist(new ReservoirMgmt(reservoirInfo));
		
		return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 저수지 정보 등록
	 * @return
	 */
	@PutMapping("reservoir")
	@ResponseBody
	public ResponseEntity<?> updateReservoir(@RequestBody List<ReservoirOperation> reservoirOperations) {
		
		if (reservoirOperationService.update(reservoirOperations))
			return new ResponseEntity<>(HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
