package net.woori.romas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.romas.domain.db.AreaLevel;
import net.woori.romas.domain.db.AreaLevel.CompositeAreaLevelPK;
import net.woori.romas.repository.AreaLevelRepository;
import net.woori.romas.service.AreaLevelService;

/**
 * 저수지 정보 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
@Transactional
public class AreaLevelServiceImpl implements AreaLevelService {

	@Autowired
	private AreaLevelRepository areaLevelRepository;
	
	@Override
	public AreaLevel get(CompositeAreaLevelPK id) {
		return areaLevelRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public List<AreaLevel> getList() {
		return areaLevelRepository.findAll();
	}

	@Override
	public boolean regist(AreaLevel domain) {
		if (isNew(domain)) {
			return areaLevelRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(AreaLevel domain) {
		if (!isNew(domain)) {
			return areaLevelRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(CompositeAreaLevelPK id) {
		areaLevelRepository.deleteById(id);
		return true;
	}

	private boolean isNew(AreaLevel domain) {
		return !areaLevelRepository.existsById(new CompositeAreaLevelPK(domain.getProvince(), domain.getCountry()));
	}

	@Transactional(readOnly = true)
	@Override
	public List<AreaLevel> findByType(int type) {
		return areaLevelRepository.findAll();
	}

}
