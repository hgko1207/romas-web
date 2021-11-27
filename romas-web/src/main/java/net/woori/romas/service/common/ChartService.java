package net.woori.romas.service.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.romas.domain.Enums.GroupType;
import net.woori.romas.domain.chart.BarChartSeries;
import net.woori.romas.domain.chart.ChartInfo;
import net.woori.romas.domain.chart.LineChartSeries;
import net.woori.romas.domain.db.ReservoirOperation;
import net.woori.romas.domain.param.FacilitySearchParam;
import net.woori.romas.domain.param.SearchParam;
import net.woori.romas.service.ReservoirLevelService;
import net.woori.romas.service.ReservoirOperationService;
import net.woori.romas.util.DateUtil;

/**
 * 차트 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
public class ChartService {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");
	
	@Autowired
	private ReservoirOperationService reservoirOperationService;
	
	@Autowired
	private ReservoirLevelService reservoirLevelService;
	
	/**
	 * 저수율 차트 정보 생성
	 * @return
	 */
	public ChartInfo createBarChartInfo() {
		
		String day = DateUtil.getDate(-1);
		
		Float value = reservoirLevelService.getRateAllList(day);
		
		BarChartSeries barChartSeries = new BarChartSeries();
		barChartSeries.addDataItem(value);
		barChartSeries.setYAxis(value);
		
		reservoirLevelService.getList(day).forEach(data -> {
			barChartSeries.addDataItem(data);
		});
		
		ChartInfo chartInfo = new ChartInfo();
		chartInfo.setBarChartSeries(barChartSeries);
		
		return chartInfo;
	}
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	public ChartInfo createLineChartInfo(SearchParam param) {
		
		ChartInfo chartInfo = new ChartInfo();
		
		String[] categories = {"1.10", "2.10", "3.10", "4.10", "5.10", "6.10", "7.10", "8.10", "9.10", "10.10", "11.10", "12.10"};
		for (String data : categories) {
			chartInfo.addCategory(data);
		}
		
		LineChartSeries lineChartSeries = new LineChartSeries("저수율");
		lineChartSeries.addDataItem(52);
		lineChartSeries.addDataItem(54);
		lineChartSeries.addDataItem(60);
		lineChartSeries.addDataItem(70);
		lineChartSeries.addDataItem(46);
		lineChartSeries.addDataItem(35);
		lineChartSeries.addDataItem(75);
		lineChartSeries.addDataItem(77);
		lineChartSeries.addDataItem(82);
		lineChartSeries.addDataItem(89);
		lineChartSeries.addDataItem(90);
		
		chartInfo.addLineChartSeries(lineChartSeries);
		
		return chartInfo;
	}
	
	/**
	 * 
	 * @param param 
	 * @return
	 */
	public List<ReservoirOperation> createGoogleChartInfo(FacilitySearchParam param) {
		
		long diffDays = DateUtil.calDiffDays(param.getStartDate(), param.getEndDate());
		param.setGroupType(diffDays > 31 ? GroupType.MONTH : GroupType.DAY);
		
//		reservoirOperationService.getList(param);
		
		List<ReservoirOperation> reservoirOperations = new ArrayList<>();
		
		reservoirLevelService.getList(param).forEach(data -> {
			String date = dateFormat.format(data.getCheckDate());
			reservoirOperations.add(new ReservoirOperation(date, 0, 0, 0, 0, 0, data.getRate()));
		});

//		reservoirOperations.add(new ReservoirOperation("1.10", 58, 1, 2, 3, 14, 52));
//		reservoirOperations.add(new ReservoirOperation("2.10", 64, 1, 2, 3, 18, 54));
//		reservoirOperations.add(new ReservoirOperation("3.10", 71, 1, 2, 4, 16, 60));
//		reservoirOperations.add(new ReservoirOperation("4.10", 68, 1, 2, 3, 14, 70));
//		reservoirOperations.add(new ReservoirOperation("5.10", 40, 2, 4, 5, 18, 46));
//		reservoirOperations.add(new ReservoirOperation("6.10", 21, 2, 4, 4, 21, 35));
//		reservoirOperations.add(new ReservoirOperation("7.10", 29.5f, 1.5f, 1, 3, 22, 75));
//		reservoirOperations.add(new ReservoirOperation("8.10", 38, 2, 4, 4, 20, 77));
//		reservoirOperations.add(new ReservoirOperation("9.10", 41, 2, 3, 3, 21, 82));
//		reservoirOperations.add(new ReservoirOperation("10.10", 48, 2, 4, 4, 17, 89));
//		reservoirOperations.add(new ReservoirOperation("11.10", 54, 2, 3, 4, 14, 87));
//		reservoirOperations.add(new ReservoirOperation("12.10", 59, 1, 2, 2, 18, 90));
		
		return reservoirOperations;
	}
}