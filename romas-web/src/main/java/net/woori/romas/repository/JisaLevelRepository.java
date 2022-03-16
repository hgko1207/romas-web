package net.woori.romas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.romas.domain.db.AreaLevel;
import net.woori.romas.domain.db.AreaLevel.CompositeAreaLevelPK;
import net.woori.romas.domain.db.JisaLevel;

public interface JisaLevelRepository extends DefaultRepository<JisaLevel, String> {
	
	@Query(value = "SELECT * FROM tb_jisa_level WHERE index_code LIKE ?1", nativeQuery = true)
	List<JisaLevel> getListContainCode(String date);
}
