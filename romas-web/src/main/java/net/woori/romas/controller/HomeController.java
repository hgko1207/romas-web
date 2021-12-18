package net.woori.romas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.woori.romas.domain.DashboardInfo;
import net.woori.romas.domain.LevelInfo;
import net.woori.romas.domain.TableInfo;
import net.woori.romas.domain.chart.ChartInfo;
import net.woori.romas.domain.db.Reservoir;
import net.woori.romas.domain.param.SearchParam;
import net.woori.romas.service.ReservoirService;
import net.woori.romas.service.common.ChartService;
import net.woori.romas.service.common.DashboardService;

/**
 * 가뭄단계현황 화면
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("home")
public class HomeController {
	
	@Autowired
	private ChartService chartService;
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private ReservoirService reservoirService;
	
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("view", "home");
		
		return "home";
	}
	
	/**
	 * 차트 정보 조회
	 * @return
	 */
	@GetMapping("chart")
	@ResponseBody
	public ChartInfo chart() {
		return chartService.createBarChartInfo();
	}
	
	/**
	 * 차트 정보 조회
	 * @return
	 */
	@GetMapping("rate")
	@ResponseBody
	public DashboardInfo rate() {
		return dashboardService.getRateInfo();
	}
	
	/**
	 * 대쉬보드 정보 조회
	 * @param name
	 * @return
	 */
	@GetMapping("dashboard")
	@ResponseBody
	public DashboardInfo dashboard(String name) {
		return dashboardService.createDashboardInfo(name);
	}
	
	/**
	 * 저수지 정보 조회
	 * @param name
	 * @return
	 */
	@GetMapping("reservoir")
	@ResponseBody
	public List<Reservoir> getReservoir() {
		return reservoirService.getList();
	}
	
	/**
	 * 저수지 정보 조회
	 * @param name
	 * @return
	 */
	@GetMapping("reservoir/level")
	@ResponseBody
	public List<LevelInfo> getReservoirLevel() {
		return reservoirService.getLevelList();
	}
	
	/**
	 * 테이블 정보 조회
	 * @param name
	 * @return
	 */
	@PostMapping("table")
	@ResponseBody
	public List<TableInfo> getTableInfo(@RequestBody SearchParam param) {
		return dashboardService.getTableInfo(param);
	}
}
