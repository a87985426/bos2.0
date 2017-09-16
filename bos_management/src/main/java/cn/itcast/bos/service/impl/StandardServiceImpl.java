package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IStandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.IStandardService;
@Service
@Scope("prototype")
@Transactional
public class StandardServiceImpl implements IStandardService {
	@Autowired
	public IStandardDao istandardao;
	@Override
	public void save(Standard standard) {
		// TODO Auto-generated method stub
		istandardao.save(standard);
	}
	@Override
	public Page<Standard> findByPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return istandardao.findAll(pageable);
	}

	@Override
	public List<Standard> findAll() {
		// TODO Auto-generated method stub
		return istandardao.findAll();
	}

}
