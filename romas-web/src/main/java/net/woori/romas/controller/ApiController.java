package net.woori.romas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.woori.romas.domain.naver.NewsInfo;
import net.woori.romas.domain.weather.WeatherInfo;
import net.woori.romas.service.common.NaverApiService;
import net.woori.romas.service.common.WthrWrnInfoService;

/**
 * API 정보 불러오기
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("api")
public class ApiController {
	
	@Autowired
	private WthrWrnInfoService weatherService;
	
	@Autowired
	private NaverApiService naverApiService;

	/**
	 * 날씨 정보 조회
	 * @return
	 */
	@GetMapping("/weather")
	@ResponseBody
	public WeatherInfo weather() {

		return weatherService.getWthrWrnInfo();
	}
	
	/**
	 * 뉴스 정보 조회
	 * @return
	 */
	@GetMapping("/news")
	@ResponseBody
	public List<NewsInfo> news() {
		
		List<NewsInfo> newsInfos = new ArrayList<>();
		newsInfos.addAll(naverApiService.getNewsInfo("한국농어촌공사"));
		newsInfos.addAll(naverApiService.getNewsInfo("가뭄"));

		return newsInfos;
	}
}
