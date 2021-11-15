package net.woori.romas.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.romas.domain.db.ReservoirOperation;
import net.woori.romas.domain.db.ReservoirOperation.CompositeOperationPK;
import net.woori.romas.domain.param.AdminSearchParam;
import net.woori.romas.repository.ReservoirOperationRepository;
import net.woori.romas.service.ReservoirOperationService;

@Service
@Transactional
public class ReservoirOperationServiceImpl implements ReservoirOperationService {

	@Autowired
	private ReservoirOperationRepository reservoirLevelRepository;
	
	@Override
	public ReservoirOperation get(CompositeOperationPK id) {
		return reservoirLevelRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReservoirOperation> getList() {
		return reservoirLevelRepository.findAll();
	}

	@Override
	public boolean regist(ReservoirOperation domain) {
		if (isNew(domain)) {
			return reservoirLevelRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(ReservoirOperation domain) {
		if (!isNew(domain)) {
			return reservoirLevelRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(CompositeOperationPK id) {
		reservoirLevelRepository.deleteById(id);
		return true;
	}

	private boolean isNew(ReservoirOperation domain) {
		return !reservoirLevelRepository.existsById(new CompositeOperationPK(domain.getFacCode(), domain.getMonth(), domain.getEml() ));
	}

	/**
	 * 관리자 페이지 사용
	 */
	@Transactional(readOnly = true)
	@Override
	public List<ReservoirOperation> getList(AdminSearchParam param) {
		
		if (param.getFacilityName().equals("전체")) {
			return reservoirLevelRepository.getList(param.getMonth(), param.getEml(), param.getRegionalHead(),
					param.getBranch());
		} 
		
		return reservoirLevelRepository.getList(param.getMonth(), param.getEml(), param.getRegionalHead(),
				param.getBranch(), param.getFacilityName());
	}

	/**
	 * 본부 정보 조회
	 */
	@Transactional(readOnly = true)
	@Override
	public List<String> getRegionalHeadList() {
		return reservoirLevelRepository.getRegionalHeadList();
	}

	/**
	 * 지사 정보 조회
	 */
	@Transactional(readOnly = true)
	@Override
	public List<String> getBranchList(String regionalHead) {
		return reservoirLevelRepository.getBranchList(regionalHead);
	}

	/**
	 * 지점 정보 조회
	 */
	@Transactional(readOnly = true)
	@Override
	public List<String> getFacilityList(String branch) {
		return reservoirLevelRepository.getFacilityNameList(branch);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ReservoirOperation> getList(String facilityName) {
		String eml = "";
		
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		if (day >= 1 && day <= 10) {
			eml = "1초순";
		} else if (day >= 11 && day <= 20) {
			eml = "2중순";
		} else if (day >= 21 && day <= 31) {
			eml = "3하순";
		}
		
		return reservoirLevelRepository.findByFacilityNameContainingAndMonthAndEml(facilityName, month, eml);
	}
}
