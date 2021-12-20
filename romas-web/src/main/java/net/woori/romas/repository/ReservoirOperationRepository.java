package net.woori.romas.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.romas.domain.db.ReservoirOperation;
import net.woori.romas.domain.db.ReservoirOperation.CompositeOperationPK;

public interface ReservoirOperationRepository extends DefaultRepository<ReservoirOperation, CompositeOperationPK>  {

	@Query(value = "SELECT regional_head FROM tb_reservoir_operation GROUP BY regional_head", nativeQuery = true)
	List<String> getRegionalHeadList();

	@Query(value = "SELECT branch FROM tb_reservoir_operation WHERE regional_head = ?1 GROUP BY branch", nativeQuery = true)
	List<String> getBranchList(String regionalHead);

	@Query(value = "SELECT * FROM tb_reservoir_operation WHERE branch = ?1 GROUP BY facility_name", nativeQuery = true)
	List<ReservoirOperation> getFacilityNameList(String branch);

	@Query(value = "SELECT * FROM tb_reservoir_operation WHERE month = ?1 AND eml = ?2 "
			+ "AND regional_head = ?3 AND branch = ?4 AND facility_name = ?5", nativeQuery = true)
	List<ReservoirOperation> getList(int month, String eml, String regionalHead, String branch, String facilityName);
	
	@Query(value = "SELECT * FROM tb_reservoir_operation WHERE month = ?1 AND eml = ?2 "
			+ "AND regional_head = ?3 AND branch = ?4", nativeQuery = true)
	List<ReservoirOperation> getList(int month, String eml, String regionalHead, String branch);

	List<ReservoirOperation> findByFacilityNameContaining(String facilityName);

	List<ReservoirOperation> findByFacilityNameContainingAndMonthAndEml(String facilityName, int month, String eml);

	@Query(value = "SELECT * FROM tb_reservoir_operation WHERE facCode = ?1", nativeQuery = true)
	List<ReservoirOperation> getList(String facCode, Date startDate, Date endDate);

	ReservoirOperation findByFacCodeAndMonthAndEml(String facCode, int month, String eml);
	
	ReservoirOperation findByFacCode(String facCode);
	
	String select = "SELECT fac_code, month, eml, regional_head, branch, facility_name, current_water_level, "
			+ "avg(attention_water_level) as attention_water_level, avg(caution_water_level) as caution_water_level, "
			+ "avg(boudary_water_level) as boudary_water_level, avg(serious_water_level) as serious_water_level";
	
	@Query(value = select + " FROM tb_reservoir_operation WHERE month = ?1 AND eml = ?2 GROUP BY regional_head", nativeQuery = true)
	List<ReservoirOperation> getRegionalHeadList(int month, String eml);
	
	@Query(value = select + " FROM tb_reservoir_operation WHERE regional_head = ?1 AND month = ?2 AND eml = ?3 GROUP BY branch", nativeQuery = true)
	List<ReservoirOperation> getBranchList(String regionalHead, int month, String eml);

}
