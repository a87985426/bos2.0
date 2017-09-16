package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.FixedAreaRepository;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.IFixedAreaService;
@Service
@Transactional
public class FixedAreaServiceImpl implements IFixedAreaService{
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	@Override
	public void save(FixedArea model) {
		// TODO Auto-generated method stub
		fixedAreaRepository.save(model);
	}
	@Override
	public Page<FixedArea> findByPage(Specification specification,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return fixedAreaRepository.findAll(specification, pageable);
	}

}
