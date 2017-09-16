package cn.itcast.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Area;

public interface IAreaService {

	public void batchSave(List<Area> list);

	public Page<Area> findByPage(Specification specification, Pageable pageable);

}
