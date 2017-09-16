package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.ICourierService;

@Service
@Transactional
public class CourierServiceImpl implements ICourierService{
	@Autowired
	private CourierRepository courierRepository;

	@Override
	public void save(Courier courier) {
		// TODO Auto-generated method stub
		courierRepository.save(courier);
	}

	@Override
	public Page<Courier> findByPage(Specification specification,Pageable pageable) {
		// TODO Auto-generated method stub
		return courierRepository.findAll(specification,pageable);
	}

	@Override
	public void delete(String[] split) {
		// TODO Auto-generated method stub
		for (int i = 0; i < split.length; i++) {
			Integer j = Integer.parseInt(split[i]);
			courierRepository.update(j);
		}
	}
	
}
