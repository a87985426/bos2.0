package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;


public interface IFixedAreaService {

	public void save(FixedArea model);

	public Page<FixedArea> findByPage(Specification specification,
			Pageable pageable);

	public void associationCourier(FixedArea model, Integer courierId,
			Integer takeTimeId);

}
