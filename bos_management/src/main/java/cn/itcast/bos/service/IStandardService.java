package cn.itcast.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Standard;

public interface IStandardService {
	public void save(Standard standard);

	public Page<Standard> findByPage(Pageable pageable);

	public List<Standard> findAll();
}
