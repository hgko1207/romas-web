package net.woori.romas.service;

import java.util.List;

import net.woori.romas.domain.db.ReservoirOperation;
import net.woori.romas.domain.db.ReservoirOperation.CompositeOperationPK;
import net.woori.romas.domain.param.AdminSearchParam;

public interface ReservoirOperationService extends CRUDService<ReservoirOperation, CompositeOperationPK> {

	List<ReservoirOperation> getList(AdminSearchParam param);

	List<String> getRegionalHeadList();

	List<String> getBranchList(String regionalHead);

	List<String> getFacilityList(String branch);

}
