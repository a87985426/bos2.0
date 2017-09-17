package cn.itcast.bos.service.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

	@Override
	public List<Courier> findNoAssocation() {
		// TODO Auto-generated method stub
		Specification<Courier> specification = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate empty = cb.isEmpty(root.get("fixedAreas").as(Set.class));
				return empty;
			}
		};
		return courierRepository.findAll(specification);
	}
	
}
