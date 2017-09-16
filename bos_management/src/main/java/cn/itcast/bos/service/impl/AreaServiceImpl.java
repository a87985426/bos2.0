package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.IAreaService;
@Service
@Transactional
public class AreaServiceImpl implements IAreaService {
	@Autowired
	private AreaRepository  areaRepository;

	@Override
	public void batchSave(List<Area> list) {
		areaRepository.save(list);
	}

	@Override
	public Page<Area> findByPage(Specification specification, Pageable pageable) {
		// TODO Auto-generated method stub
		return areaRepository.findAll(specification, pageable);
	}

	
}
