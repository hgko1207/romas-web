package net.woori.romas.service.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.romas.domain.DashboardInfo;
import net.woori.romas.domain.TableInfo;
import net.woori.romas.domain.DashboardInfo.UpDown;
import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirOperation.OperationType;
import net.woori.romas.domain.param.SearchParam;
import net.woori.romas.service.ReservoirLevelService;
import net.woori.romas.service.ReservoirOperationService;

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
	 * 대쉬보드 테이블 정보 조회
	 * @param param
	 * @return
	 */
	public List<TableInfo> getTableInfo(SearchParam param) {
		
		List<TableInfo> tableInfos = new ArrayList<>();
		
		if (param.getType() == 1) {
			tableInfos.add(new TableInfo("경기", OperationType.Attention, 673));
			tableInfos.add(new TableInfo("강원", OperationType.Caution, 417));
			tableInfos.add(new TableInfo("충북", OperationType.Attention, 526));
			tableInfos.add(new TableInfo("충남", OperationType.Serious, 103));
			tableInfos.add(new TableInfo("전북", OperationType.Attention, 673));
			tableInfos.add(new TableInfo("전남", OperationType.Caution, 470));
			tableInfos.add(new TableInfo("경북", OperationType.Attention, 570));
			tableInfos.add(new TableInfo("경남", OperationType.Attention, 560));
			tableInfos.add(new TableInfo("제주", OperationType.Attention, 610));
		} else if (param.getType() == 2) {
			tableInfos.add(new TableInfo("강화.옹진", OperationType.Attention, 91));
			tableInfos.add(new TableInfo("김포", OperationType.Caution, 34));
			tableInfos.add(new TableInfo("안성", OperationType.Caution, 65));
			tableInfos.add(new TableInfo("양평.광주.서울", OperationType.Attention, 98));
			tableInfos.add(new TableInfo("여주.이천", OperationType.Boudary, 40));
			tableInfos.add(new TableInfo("연천.포천.가평", OperationType.Boudary, 58));
			tableInfos.add(new TableInfo("파주", OperationType.Attention, 96));
			tableInfos.add(new TableInfo("평택", OperationType.Attention, 92));
			tableInfos.add(new TableInfo("화성.수원", OperationType.Caution, 88));
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
