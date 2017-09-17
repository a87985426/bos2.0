package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.CourierRepository;
import cn.itcast.bos.dao.FixedAreaRepository;
import cn.itcast.bos.dao.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.IFixedAreaService;
@Service
@Transactional
public class FixedAreaServiceImpl implements IFixedAreaService{
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	@Autowired
	private CourierRepository courierRepository;
	@Autowired
	private TakeTimeRepository takeTimeRepository;
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
	@Override
	public void associationCourier(FixedArea model, Integer courierId,
			Integer takeTimeId) {
		// TODO Auto-generated method stub
		FixedArea fixedArea = fixedAreaRepository.findOne(model.getId());
		Courier courier = courierRepository.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		//快递员和定去关联,对于集合元素先get在往集合里面加入(这里要是persistent对象才可以)
		fixedArea.getCouriers().add(courier);
		courier.setTakeTime(takeTime);
	}

}
