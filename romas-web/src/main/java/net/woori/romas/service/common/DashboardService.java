package net.woori.romas.service.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.romas.domain.DashboardInfo;
import net.woori.romas.domain.DashboardInfo.UpDown;
import net.woori.romas.service.ReservoirLevelService;

/**
 * 대쉬보드 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
public class DashboardService {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	@Autowired
	private ReservoirLevelService reservoirLevelService;

	/**
	 * 대쉬보드 정보 생성
	 * @param name
	 * @return
	 */
	public DashboardInfo createDashboardInfo(String name) {
		
		String day1 = getDate(-1);
		String day2 = getDate(-2);
		
		float value1 = 0;
		float value2 = 0;
		
		if (name.equals("전국")) {
			value1 = reservoirLevelService.getAllList(day1);
			value2 = reservoirLevelService.getAllList(day2);
		} else {
			value1 = reservoirLevelService.getList(day1, name);
			value2 = reservoirLevelService.getList(day2, name);
		}
		
		DashboardInfo dashboardInfo = new DashboardInfo(name);
		dashboardInfo.setValue(Math.round(value1));
		
		if (value1 > value2) {
			dashboardInfo.setUpDown(UpDown.UP);
			dashboardInfo.setGap((int)Math.round((value1 / value2 - 1) * 100));
		} else if (value1 == value2) {
			dashboardInfo.setUpDown(UpDown.EQUAL);
			dashboardInfo.setGap(0);
		} else {
			dashboardInfo.setUpDown(UpDown.DOWN);
			dashboardInfo.setGap((int)Math.round(value2 / value1 * 100));
		}
		
		return dashboardInfo;
	}
	
	/**
	 * 날짜 가져오기
	 * @param value
	 * @return
	 */
	private String getDate(int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, value);
		
		return dateFormat.format(calendar.getTime());
	}
}
