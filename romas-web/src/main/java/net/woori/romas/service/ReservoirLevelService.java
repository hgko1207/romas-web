package net.woori.romas.service;

import java.util.List;

import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirLevel.CompositePK;

public interface ReservoirLevelService extends CRUDService<ReservoirLevel, CompositePK> {

	void regist(List<ReservoirLevel> reservoirLevels);
	
	float getAllList(String date);
	
	float getList(String date, String area);

}
