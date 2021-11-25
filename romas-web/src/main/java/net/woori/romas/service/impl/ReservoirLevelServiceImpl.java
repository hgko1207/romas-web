package net.woori.romas.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
	
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
		return reservoirLevelRepository.getAllList(date);
	}

	@Transactional(readOnly = true)
	@Override
	public float getList(String date, String area) {
		return reservoirLevelRepository.getList(date, area);
	}

	@Override
	public List<ReservoirLevel> getList(FacilitySearchParam param) {
		
		String startDate = dateFormat.format(param.getStartDate()) + " 00:00:00";
		String endDate = dateFormat.format(param.getEndDate()) + " 23:59:59";
		
		return reservoirLevelRepository.getList(param.getFacCode(), startDate, endDate);
	}
}
