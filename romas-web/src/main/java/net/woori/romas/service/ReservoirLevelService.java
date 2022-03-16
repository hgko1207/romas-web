package net.woori.romas.service;

import java.util.List;

import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirLevel.CompositePK;
import net.woori.romas.domain.param.FacilitySearchParam;

public interface ReservoirLevelService extends CRUDService<ReservoirLevel, CompositePK> {

	void regist(List<ReservoirLevel> reservoirLevels);
	
	float getAllList(String date);
	
	float getRegionalList(String date, String area);

	List<ReservoirLevel> getList(FacilitySearchParam param);
	
	List<Float> getList(String date);

	float getAvgList(String startDate, String endDate);
	
	ReservoirLevel getLastOne(String facCode);
	
	float getBranchList(String date, String branch);
	
	float getRateAllList(String date);

	float getFacCodeList(String date, String facCode);

}
