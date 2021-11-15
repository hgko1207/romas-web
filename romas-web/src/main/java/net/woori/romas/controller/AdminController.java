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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirOperation;
import net.woori.romas.domain.param.AdminSearchParam;
import net.woori.romas.service.ReservoirOperationService;
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
	private ReservoirOperationService reservoirOperationService;
	
	@Autowired
	private ReservoirInfoService reservoirInfoService;

	/**
	 * 관리자 화면
	 * @param model
	 */
	@GetMapping("")
	public void admin(Model model) {
		
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
				List<String> facilityNames = reservoirOperationService.getFacilityList(branchs.get(0));
				model.addAttribute("facilityNames", facilityNames);
			}
		}

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
}
