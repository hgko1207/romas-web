package net.woori.romas.service;

import java.util.List;

import net.woori.romas.domain.LevelInfo;
import net.woori.romas.domain.db.Reservoir;

public interface ReservoirService extends CRUDService<Reservoir, String> {

	List<LevelInfo> getLevelList();
	
	List<Reservoir> getListFromBranch(String regionalHead);
	
	List<Reservoir> getListFromFacility(String branch);
}
