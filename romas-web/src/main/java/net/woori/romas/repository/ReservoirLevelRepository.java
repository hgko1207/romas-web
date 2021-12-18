package net.woori.romas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirLevel.CompositePK;

public interface ReservoirLevelRepository extends DefaultRepository<ReservoirLevel, CompositePK> {

	final String SELECT = "check_date, fac_code, country, fac_name, water_level, create_date, branch, regional_head";
	
	@Query(value = "SELECT round(avg(water_level)) FROM tb_reservoir_level WHERE create_date = ?1", nativeQuery = true)
	float getAllList(String date);
	
	@Query(value = "SELECT round(avg(water_level)) FROM tb_reservoir_level WHERE create_date = ?1 AND regional_head = ?2", nativeQuery = true)
	float getRegionalList(String date, String area);
	
	@Query(value = "SELECT round(avg(water_level)) FROM tb_reservoir_level WHERE create_date = ?1 AND branch = ?2", nativeQuery = true)
	Float getBranchList(String date, String branch);

	@Query(value = "SELECT round(avg(rate)) FROM tb_reservoir_level WHERE create_date = ?1", nativeQuery = true)
	Float getRateAllList(String date);
	
	@Query(value = "SELECT round(avg(rate)) FROM tb_reservoir_level WHERE create_date = ?1 GROUP BY regional_head ORDER BY regional_head", nativeQuery = true)
	List<Float> getList(String date);

	@Query(value = "SELECT * FROM tb_reservoir_level WHERE fac_code = ?1 AND check_date BETWEEN ?2 ANd ?3", nativeQuery = true)
	List<ReservoirLevel> getList(String facCode, String startDate, String endDate);

	@Query(value = "SELECT round(avg(rate)) FROM tb_reservoir_level WHERE check_date BETWEEN ?1 ANd ?2", nativeQuery = true)
	Float getAvgList(String startDate, String endDate);

	@Query(value = "SELECT " + SELECT + ", round(avg(rate)) as rate FROM tb_reservoir_level WHERE fac_code = ?1 AND check_date BETWEEN ?2 ANd ?3 GROUP BY MONTH(check_date)", nativeQuery = true)
	List<ReservoirLevel> getMonthGroupList(String facCode, String startDate, String endDate);
}
