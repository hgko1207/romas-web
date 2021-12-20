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

	Float getAvgList(String startDate, String endDate);
	
	ReservoirLevel getLastOne(String facCode);
	
	Float getBranchList(String date, String branch);
	
	Float getRateAllList(String date);

	Float getFacCodeList(String date, String facCode);

}
