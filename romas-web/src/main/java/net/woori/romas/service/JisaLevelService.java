package net.woori.romas.service;

import java.util.List;

import net.woori.romas.domain.db.AreaLevel;
import net.woori.romas.domain.db.AreaLevel.CompositeAreaLevelPK;
import net.woori.romas.domain.db.JisaLevel;

public interface JisaLevelService extends CRUDService<JisaLevel, String> {

	List<JisaLevel> getListContainCode(String date);
}
