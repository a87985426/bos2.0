package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;

public interface ICourierService {

	public void save(Courier courier);


	public Page<Courier> findByPage(Specification specification, Pageable pageable);


	public void delete(String[] split);
	
}
