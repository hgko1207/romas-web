package net.woori.romas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.romas.domain.LevelInfo;
import net.woori.romas.domain.db.Reservoir;

public interface ReservoirRepository extends DefaultRepository<Reservoir, String> {

	@Query(value = "SELECT area_spark as code, round(avg(level)) as level FROM tb_reservoir group by area_spark", nativeQuery = true)
	List<LevelInfo> getLevelList();
}
