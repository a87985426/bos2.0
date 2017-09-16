package cn.itcast.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.ICustomerService;
@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService{
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findNoAssociationCustomer() {
		// TODO Auto-generated method stub
		return customerRepository.findByFixedAreaNumIsNull();
	}

	@Override
	public List<Customer> findHasAssociationCustomer(String fixedAreaNum) {
		// TODO Auto-generated method stub
		return customerRepository.findByFixedAreaNum(fixedAreaNum);
	}

	@Override
	public void updataAssocation(String fixedAreaNum, String customerIdStr) {
		// TODO Auto-generated method stub
		customerRepository.clear(fixedAreaNum);
		if ("unexist".equals(customerIdStr)) {
			return;
		}
		String[] split = customerIdStr.split(",");
		for (String s : split) {
			Integer id = Integer.parseInt(s);
			customerRepository.updateAssocation(fixedAreaNum,id);
		}
	}

}
