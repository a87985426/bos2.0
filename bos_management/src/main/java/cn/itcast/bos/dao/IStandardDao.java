package cn.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.Standard;

public interface IStandardDao extends JpaRepository<Standard, Integer>{
	
}
