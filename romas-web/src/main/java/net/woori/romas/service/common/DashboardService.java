package net.woori.romas.service.common;

import org.springframework.stereotype.Service;

import net.woori.romas.domain.DashboardInfo;

/**
 * 대쉬보드 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
public class DashboardService {

	/**
	 * 대쉬보드 정보 생성
	 * @param name
	 * @return
	 */
	public DashboardInfo createDashboardInfo(String name) {
		
		DashboardInfo dashboardInfo = new DashboardInfo(name);
		
		if (name.equals("전국")) {
			dashboardInfo.setValue(734);
			dashboardInfo.setGap(83);
			dashboardInfo.setUp(false);
		} else if (name.equals("강원")) {
			dashboardInfo.setValue(734);
			dashboardInfo.setGap(83);
			dashboardInfo.setUp(true);
		} else {
			dashboardInfo.setValue(673);
			dashboardInfo.setGap(70);
			dashboardInfo.setUp(false);
		}
		
		return dashboardInfo;
	}
}
