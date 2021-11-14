package net.woori.romas.repository;

import org.springframework.data.jpa.repository.Query;

import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirLevel.CompositePK;

public interface ReservoirLevelRepository extends DefaultRepository<ReservoirLevel, CompositePK> {

	@Query(value = "SELECT round(avg(water_level)) FROM tb_reservoir_level WHERE create_date = ?1", nativeQuery = true)
	float getAllList(String date);
	
	@Query(value = "SELECT round(avg(water_level)) FROM tb_reservoir_level WHERE create_date = ?1 AND regional_head = ?2", nativeQuery = true)
	float getList(String date, String area);
}
