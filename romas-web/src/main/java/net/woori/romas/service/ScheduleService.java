package net.woori.romas.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import net.woori.romas.domain.db.AreaLevel;
import net.woori.romas.domain.db.Reservoir;
import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirOperation;

/**
 * 주기적으로 업데이트해야 될 스케쥴 관리
 * 
 * @author 
 * 
 */
@Service
@Transactional
public class ScheduleService {
	
	private static final int UPDATE_TIME_MILLISECONDS = 12 * 60 * 60 * 1000; // 1시간을 기준으로
	private static final int INIT_TIME_MILLISECONDS = 1 * 10 * 1000;

	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ReservoirService reservoirService;

	@Autowired
	private ReservoirOperationService reservoirOperationService;

	@Autowired
	private ReservoirLevelService reservoirLevelService;
	
	@Autowired
	private AreaLevelService areaLevelService;

	@Autowired
	private TransactionTemplate txTemplate;
	
	//@Scheduled(fixedDelay = UPDATE_TIME_MILLISECONDS, initialDelay = INIT_TIME_MILLISECONDS)
	public void areaLevelUpdate() {
		txTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) 
			{
				for(AreaLevel al : areaLevelService.getList())
				{
					System.err.println("지금 계산하고 있는 지역이름 : " + al.getLabel());
					al.setLevel(0); //계산전에 모두 관심으로 변경
					int doLevelSum=0;
					int doAreaCount=0;		
					int levelSum=0;
					int areaCount=0;
					for(Reservoir rv : reservoirService.getList())
					{
						if(al.getType() == 2) //시군구 계산
						{
							//System.err.println("시군해당됨");
							if(rv.getAreaSiGun() != null) {
								if(rv.getAreaSiGun() == al.getCountry())
								{
									areaCount++;
									levelSum += rv.getLevel();
								}
							}
						}
						//광역시, 도 계산
						//System.err.println("광역시, 도 해당됨");
						if(rv.getAreaSpark() == al.getProvince())
						{
							doAreaCount++;
							doLevelSum += rv.getLevel();
						}
						
					}
					//지역 레벨 계산
					int level = 0;
					if(al.getType() == 2) {
						if(levelSum != 0) level = levelSum / areaCount;
						if(level > 3) al.setLevel(3);
						else al.setLevel(level);
						System.err.println("level sum:"+levelSum+",count:"+areaCount+",level 계산 결과: "+level+", 지역 이름 : " + al.getLabel());
					}else {
						if(doLevelSum != 0) level = doLevelSum / doAreaCount;
						if(level > 3) al.setLevel(3);
						else al.setLevel(level);
						System.err.println("level sum:"+doLevelSum+",count:"+doAreaCount+",level 계산 완료 : "+level+", 지역 이름 : " + al.getLabel());
					}
					areaLevelService.update(al);
				}
			}
		});
	}
	
	//@Scheduled(fixedDelay = UPDATE_TIME_MILLISECONDS, initialDelay = INIT_TIME_MILLISECONDS)
	public void levelUpdate() {
		txTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				//지역별 레벨 계산 추가
				
				for(Reservoir rv : reservoirService.getList())
				{
					System.err.println("TEST rv " + rv.getFacCode());
					Date date = new Date(System.currentTimeMillis());
					ReservoirOperation ro = reservoirOperationService.get(rv.getFacCode(),date);
					ReservoirLevel rl = reservoirLevelService.getLastOne(rv.getFacCode());
					
					if (rl != null && ro != null) {
						ro.setCurrentWaterLevel(rl.getWaterLevel());
						reservoirOperationService.update(ro);
					}
					
					if (ro != null) {
						if (ro.getCurrentWaterLevel() >= ro.getCautionWaterLevel())
							rv.setLevel(0); // 관심
						else if (ro.getCurrentWaterLevel() > ro.getBoudaryWaterLevel()
								&& ro.getCurrentWaterLevel() <= ro.getCautionWaterLevel())
							rv.setLevel(1); // 주의
						else if (ro.getCurrentWaterLevel() > ro.getSeriousWaterLevel()
								&& ro.getCurrentWaterLevel() <= ro.getBoudaryWaterLevel())
							rv.setLevel(2); // 경계
						else if (ro.getCurrentWaterLevel() <= ro.getSeriousWaterLevel())
							rv.setLevel(3); // 심각
						
						reservoirService.update(rv);
						System.err.println("TEST rv update" + rv.toString());
					}
				}
			}
		});
	}
}
