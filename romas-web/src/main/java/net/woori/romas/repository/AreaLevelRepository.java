package net.woori.romas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.romas.domain.db.AreaLevel;
import net.woori.romas.domain.db.AreaLevel.CompositeAreaLevelPK;

public interface AreaLevelRepository extends DefaultRepository<AreaLevel, CompositeAreaLevelPK> {
	
	List<AreaLevel> findByType(int type);
	
	@Query(value = "SELECT * FROM tb_area_level where label = ?1", nativeQuery = true)
	AreaLevel getLabel(String name);
}
