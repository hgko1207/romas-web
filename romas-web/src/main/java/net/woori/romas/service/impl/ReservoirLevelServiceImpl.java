package net.woori.romas.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.romas.domain.Enums.GroupType;
import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirLevel.CompositePK;
import net.woori.romas.domain.param.FacilitySearchParam;
import net.woori.romas.repository.ReservoirLevelRepository;
import net.woori.romas.service.ReservoirLevelService;

/**
 * 저수지 수위 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
@Transactional
public class ReservoirLevelServiceImpl implements ReservoirLevelService {

	@Autowired
	private ReservoirLevelRepository reservoirLevelRepository;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public ReservoirLevel get(CompositePK id) {
		return reservoirLevelRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReservoirLevel> getList() {
		return reservoirLevelRepository.findAll();
	}

	@Override
	public boolean regist(ReservoirLevel domain) {
		if (isNew(domain)) {
			return reservoirLevelRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(ReservoirLevel domain) {
		if (!isNew(domain)) {
			return reservoirLevelRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(CompositePK id) {
		reservoirLevelRepository.deleteById(id);
		return true;
	}

	private boolean isNew(ReservoirLevel domain) {
		return !reservoirLevelRepository.existsById(new CompositePK(domain.getCheckDate(), domain.getFacCode()));
	}

	@Override
	public void regist(List<ReservoirLevel> reservoirLevels) {
		reservoirLevelRepository.saveAll(reservoirLevels);
	}

	@Transactional(readOnly = true)
	@Override
	public float getAllList(String date) {
		Float result = reservoirLevelRepository.getAllList(date);
		return result == null ? 0 : result.floatValue();
	}

	/**
	 * 본부를 통해 평균데이터 조회
	 */
	@Transactional(readOnly = true)
	@Override
	public float getRegionalList(String date, String area) {
		Float result = reservoirLevelRepository.getRegionalList(date, area);
		return result == null ? 0 : result.floatValue();
	}
	
	/**
	 * 지사를 통해 평균데이터 조회
	 */
	@Transactional(readOnly = true)
	@Override
	public float getBranchList(String date, String branch) {
		Float result = reservoirLevelRepository.getBranchList(date, branch);
		return result == null ? 0 : result.floatValue();
	}
	
	/**
	 * 시설코드를 통해 수위 조회
	 */
	@Override
	public float getFacCodeList(String date, String facCode) {
		Float result = reservoirLevelRepository.getFacCodeList(date, facCode);
		return result == null ? 0 : result.floatValue();
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReservoirLevel> getList(FacilitySearchParam param) {
		
		String startDate = dateFormat.format(param.getStartDate()) + " 00:00:00";
		String endDate = dateFormat.format(param.getEndDate()) + " 23:59:59";
		
		if (param.getGroupType() == GroupType.DAY) {
			return reservoirLevelRepository.getList(param.getFacCode(), startDate, endDate);
		} else {
			return reservoirLevelRepository.getMonthGroupList(param.getFacCode(), startDate, endDate);
		}
	}

	/**
	 * 전국 저수율 평균 조회
	 */
	@Transactional(readOnly = true)
	@Override
	public float getRateAllList(String date) {
		Float result = reservoirLevelRepository.getRateAllList(date);
		return result == null ? 0 : result.floatValue();
	}

	/**
	 * 지역별로 저수율 평균 조회
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Float> getList(String date) {
		return reservoirLevelRepository.getList(date);
	}

	@Transactional(readOnly = true)
	@Override
	public float getAvgList(String startDate, String endDate) {
		Float result = reservoirLevelRepository.getAvgList(startDate, endDate);
		return result == null ? 0 : result.floatValue();
	}

	@Override
	public ReservoirLevel getLastOne(String facCode) {
		return reservoirLevelRepository.getLastOne(facCode);
	}
}
