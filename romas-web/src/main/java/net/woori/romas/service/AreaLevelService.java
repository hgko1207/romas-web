package net.woori.romas.service;

import java.util.List;

import net.woori.romas.domain.db.AreaLevel;
import net.woori.romas.domain.db.AreaLevel.CompositeAreaLevelPK;

public interface AreaLevelService extends CRUDService<AreaLevel, CompositeAreaLevelPK> {

	List<AreaLevel> getListFromProvince();
	
	AreaLevel findByCountry(int country);
}
