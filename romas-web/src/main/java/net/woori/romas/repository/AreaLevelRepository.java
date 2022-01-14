package net.woori.romas.repository;

import java.util.List;

import net.woori.romas.domain.db.AreaLevel;
import net.woori.romas.domain.db.AreaLevel.CompositeAreaLevelPK;

public interface AreaLevelRepository extends DefaultRepository<AreaLevel, CompositeAreaLevelPK> {
	
	List<AreaLevel> findByType(int type);
}
