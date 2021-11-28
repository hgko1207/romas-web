package net.woori.romas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.romas.domain.db.Reservoir;
import net.woori.romas.repository.ReservoirRepository;
import net.woori.romas.service.ReservoirService;

/**
 * 저수지 정보 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
@Transactional
public class ReservoirServiceImpl implements ReservoirService {

	@Autowired
	private ReservoirRepository reservoirRepository;
	
	@Override
	public Reservoir get(String id) {
		return reservoirRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Reservoir> getList() {
		return reservoirRepository.findAll();
	}

	@Override
	public boolean regist(Reservoir domain) {
		if (isNew(domain)) {
			return reservoirRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(Reservoir domain) {
		if (!isNew(domain)) {
			return reservoirRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(String id) {
		reservoirRepository.deleteById(id);
		return true;
	}

	private boolean isNew(Reservoir domain) {
		return !reservoirRepository.existsById(domain.getFacCode());
	}
}
