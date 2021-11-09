package net.woori.romas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.romas.domain.db.ReservoirMgmt;
import net.woori.romas.repository.ReservoirMgmtRepository;
import net.woori.romas.service.ReservoirMgmtService;

@Service
@Transactional
public class ReservoirMgmtServiceImpl implements ReservoirMgmtService {

	@Autowired
	private ReservoirMgmtRepository reservoirMgmtRepository;
	
	@Override
	public ReservoirMgmt get(String id) {
		return reservoirMgmtRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReservoirMgmt> getList() {
		return reservoirMgmtRepository.findAll();
	}

	@Override
	public boolean regist(ReservoirMgmt domain) {
		if (isNew(domain)) {
			return reservoirMgmtRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(ReservoirMgmt domain) {
		if (!isNew(domain)) {
			return reservoirMgmtRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(String id) {
		reservoirMgmtRepository.deleteById(id);
		return true;
	}

	private boolean isNew(ReservoirMgmt domain) {
		return !reservoirMgmtRepository.existsById(domain.getFacCode());
	}
}
