package cn.itcast.test;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.ICustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerTest {
	@Autowired
	private ICustomerService customerServiceImpl;
	@Autowired
	private CustomerRepository cusotmerRepository;
	
	@Test
	public void test() {
		Collection<? extends Customer> collection = 
				WebClient.create("http://localhost:9080/crm_management/services/")
				.path("customerService/findNoAssociationCustomer")
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		//List<Customer> list = cusotmerRepository.findByFixedAreaNumIsNull();
		System.out.println(collection);
	}
	@Test
	public void test1() {
//		Collection<? extends Customer> collection = WebClient.create("http://localhost:9080/crm_management/services/")
//				.path("customerService/findHasAssociationCustomer/dq001")
//				.accept(MediaType.APPLICATION_JSON)
//				.getCollection(Customer.class);
		List<Customer> list = cusotmerRepository.findByFixedAreaNum("dq001");
		System.out.println(list);
	}
	@Test
	public void test2() {
		Customer customer = WebClient.create("http://localhost:9080/crm_management/services/customerService/findCustomerByTelephone/13812345678")
		.accept(MediaType.APPLICATION_JSON).get(Customer.class);
		System.out.println(customer);
//		Customer customer = cusotmerRepository.findByTelephone("13812345678");
//		System.out.println(customer);
	}
	@Test
	public void test3(){
		WebClient.create("http://localhost:9080/crm_management/services/customerService/updateType/13812345678")
		.put(null);
	}
}
