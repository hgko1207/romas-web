package net.woori.romas.service.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.romas.domain.DashboardInfo;
import net.woori.romas.domain.DashboardInfo.UpDown;
import net.woori.romas.domain.TableInfo;
import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirOperation;
import net.woori.romas.domain.db.ReservoirOperation.OperationType;
import net.woori.romas.domain.param.SearchParam;
import net.woori.romas.service.ReservoirLevelService;
import net.woori.romas.service.ReservoirOperationService;
import net.woori.romas.util.DateUtil;

/**
 * 대쉬보드 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
public class DashboardService {
	
	@Autowired
	private ReservoirLevelService reservoirLevelService;
	
	@Autowired
	private ReservoirOperationService reservoirOperationService;
	
	@Autowired
	private ReservoirInfoService reservoirInfoService;
	
	/**
	 * 대쉬보드 정보 생성
	 * @param name
	 * @return
	 */
	public DashboardInfo createDashboardInfo(String name) {
		
		String day1 = DateUtil.getDate(-1);
		String day2 = DateUtil.getDate(-2);
		
		float value1 = 0;
		float value2 = 0;
		
		if (name.equals("전국")) {
			value1 = reservoirLevelService.getAllList(day1);
			value2 = reservoirLevelService.getAllList(day2);
		} else {
			value1 = reservoirLevelService.getRegionalList(day1, name);
			value2 = reservoirLevelService.getRegionalList(day2, name);
		}
		
		DashboardInfo dashboardInfo = new DashboardInfo(name);
		dashboardInfo.setValue(Math.round(value1));
		
		if (value1 > value2) {
			dashboardInfo.setUpDown(UpDown.UP);
			//dashboardInfo.setGap((int)Math.round((value1 / value2 - 1) * 100));
			dashboardInfo.setGap((int)Math.round((value1 / value2) * 100));
		} else if (value1 == value2) {
			dashboardInfo.setUpDown(UpDown.EQUAL);
			dashboardInfo.setGap(0);
		} else {
			dashboardInfo.setUpDown(UpDown.DOWN);
			//dashboardInfo.setGap((int)Math.round(value2 / value1 * 100));
			dashboardInfo.setGap((int)Math.round(value2 / value1) * 100);
		}
		
		return dashboardInfo;
	}
	
	/**
	 * 대쉬보드 테이블 정보 조회
	 * @param param
	 * @return
	 */
	public List<TableInfo> getTableInfo(SearchParam param) {
		
		List<TableInfo> tableInfos = new ArrayList<>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		String eml = getEml(day);
		
		if (param.getType() == 1) {
			reservoirOperationService.getListFromRegionalHead(month, eml).forEach(data -> {
				float value = reservoirLevelService.getRegionalList(DateUtil.getDate(-1), data.getRegionalHead());
				tableInfos.add(new TableInfo(data.getRegionalHead(), getType(data, value), value));
			});
		} else if (param.getType() == 2) {
			reservoirOperationService.getListFromBranch(param.getRegionalHead(), month, eml).forEach(data -> {
				Float value = reservoirLevelService.getBranchList(DateUtil.getDate(-1), data.getBranch());
				if (value != null)
					tableInfos.add(new TableInfo(data.getBranch(), getType(data, value), value));
			});
		} else if (param.getType() == 3) {
			if (!param.getFacilityName().isEmpty() ) {
				reservoirOperationService.getList(param.getFacilityName()).forEach(data -> {
					TableInfo tableInfo = new TableInfo();
					tableInfo.setName(data.getFacilityName());
					tableInfo.setType(OperationType.Attention);
					
					List<ReservoirLevel> reservoirLevels = reservoirInfoService.getReservoirWaterLevel(data.getFacCode());
					if (reservoirLevels.size() > 0) {
						tableInfo.setWaterLevel(reservoirLevels.get(0).getWaterLevel());
					}
					
					tableInfos.add(tableInfo);
				});
			}
		}
		
		return tableInfos;
	}
	
	private OperationType getType(ReservoirOperation operation, float value) {
	
		if (operation.getSeriousWaterLevel() <= value && value < operation.getBoudaryWaterLevel()) {
			return OperationType.Serious;
		} else if (operation.getBoudaryWaterLevel() <= value && value < operation.getCautionWaterLevel()) {
			return OperationType.Boudary;
		} else if (operation.getCautionWaterLevel() <= value && value < operation.getAttentionWaterLevel()) {
			return OperationType.Caution;
		} else if (operation.getAttentionWaterLevel() <= value) {
			return OperationType.Attention;
		} else {
			return OperationType.Serious;
		}
	}

	/**
	 * 저수율 조회
	 * @return
	 */
	public DashboardInfo getRateInfo() {
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		
		String startDate = year + "-01-01";
		String endDate = year + "-12-31";
		
		Float value1 = reservoirLevelService.getRateAllList(DateUtil.getDate(-1));
		Float value2 = reservoirLevelService.getAvgList(startDate, endDate);
		
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setTodayValue(value1);
		dashboardInfo.setCommonYearValue(value2);
		
		if (value1 > value2) {
			dashboardInfo.setGap((int)Math.round((value1 / value2) * 100));
		} else if (value1 == value2) {
			dashboardInfo.setGap(100);
		} else {
			dashboardInfo.setGap((int)Math.round(value2 / value1) * 100);
		}
		
		return dashboardInfo;
	}
	
	private String getEml(int day) {

		String eml = "";

		if (day >= 1 && day <= 10) {
			eml = "1초순";
		} else if (day >= 11 && day <= 20) {
			eml = "2중순";
		} else if (day >= 21 && day <= 31) {
			eml = "3하순";
		}

		return eml;
	}
}
