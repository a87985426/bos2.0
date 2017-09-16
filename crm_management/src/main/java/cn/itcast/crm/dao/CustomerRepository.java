package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Integer>{

	public List<Customer> findByFixedAreaNumIsNull();

	public List<Customer> findByFixedAreaNum(String fixedAreaNum);
	@Query("update Customer set fixedAreaNum= null where fixedAreaNum=?")
	@Modifying
	public void clear(String fixedAreaNum);
	@Query("update Customer set fixedAreaNum= ? where id=?")
	@Modifying
	public void updateAssocation(String fixedAreaNum, Integer id);

}
