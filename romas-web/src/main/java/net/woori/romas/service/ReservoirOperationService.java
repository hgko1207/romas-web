package net.woori.romas.service;

import java.util.Date;
import java.util.List;

import net.woori.romas.domain.db.ReservoirOperation;
import net.woori.romas.domain.db.ReservoirOperation.CompositeOperationPK;
import net.woori.romas.domain.param.AdminSearchParam;
import net.woori.romas.domain.param.FacilitySearchParam;

public interface ReservoirOperationService extends CRUDService<ReservoirOperation, CompositeOperationPK> {

	List<ReservoirOperation> getList(AdminSearchParam param);
	
	List<ReservoirOperation> getList(String facilityName);

	List<String> getRegionalHeadList();

	List<String> getBranchList(String regionalHead);

	List<ReservoirOperation> getFacilityList(String branch);

	List<ReservoirOperation> getList(FacilitySearchParam param);

	ReservoirOperation get(String facCode, Date checkDate);
	
	boolean update(List<ReservoirOperation> reservoirOperations);

	List<ReservoirOperation> getListFromRegionalHead(int month, String eml);

	List<ReservoirOperation> getListFromBranch(String regionalHead, int month, String eml);
	
	List<ReservoirOperation> getList(String branch, int month, String eml);
	
	List<ReservoirOperation> getList(String regionalHead, String branch, int month, String eml);
	
}
