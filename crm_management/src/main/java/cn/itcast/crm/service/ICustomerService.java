package cn.itcast.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import cn.itcast.crm.domain.Customer;
@Produces("*/*")
public interface ICustomerService {
	@GET
	@Path("/findNoAssociationCustomer")
	@Produces({ "application/xml", "application/json" })
	public List<Customer> findNoAssociationCustomer();
	
	@GET
	@Path("/findHasAssociationCustomer/{fixedAreaNum}")
	@Produces({ "application/xml", "application/json" })
	public List<Customer> findHasAssociationCustomer(@PathParam("fixedAreaNum") String fixedAreaNum);
	
	@PUT
	@Path("/updataAssocation")
	public void updataAssocation(@QueryParam("fixedAreaNum") String fixedAreaNum ,@QueryParam("customerIdStr") String customerIdStr);
	
	@POST
	@Path("/saveCustomer")
	@Consumes({ "application/xml", "application/json" })
	public void saveCustomer(Customer customer);
	
	@GET
	@Path("/findCustomerByTelephone/{telephone}")
	@Produces({ "application/xml", "application/json" })
	public Customer findCustomerByTelephone(@PathParam("telephone")String telephone);
	@PUT
	@Path("/updateType/{telephone}")
	public void updateType(@PathParam("telephone")String telephone);
}
