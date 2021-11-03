package net.woori.romas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.domain.db.ReservoirLevel.CompositePK;
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
}
