package net.woori.romas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.romas.domain.db.JisaLevel;
import net.woori.romas.repository.JisaLevelRepository;
import net.woori.romas.service.JisaLevelService;

/**
 * 저수지 정보 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
@Transactional
public class JisaLevelServiceImpl implements JisaLevelService {

	@Autowired
	private JisaLevelRepository jisaLevelRepository;
	
	@Transactional(readOnly = true)
	@Override
	public JisaLevel get(String id) {
		return jisaLevelRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public List<JisaLevel> getList() {
		return jisaLevelRepository.findAll();
	}

	@Override
	public boolean regist(JisaLevel domain) {
		if (isNew(domain)) {
			return jisaLevelRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(JisaLevel domain) {
		if (!isNew(domain)) {
			return jisaLevelRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(String id) {
		jisaLevelRepository.deleteById(id);
		return true;
	}

	private boolean isNew(JisaLevel domain) {
		return !jisaLevelRepository.existsById(domain.getIndexCode());
	}

	@Override
	public List<JisaLevel> getListContainCode(String date) {
		return jisaLevelRepository.getListContainCode(date);
	}
}
