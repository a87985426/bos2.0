package cn.itcast.bos.web.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;







import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.IFixedAreaService;
import cn.itcast.crm.domain.Customer;
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("protopyte")
public class FixedAreaAction extends BaseAction<FixedArea>{

	@Autowired
	private IFixedAreaService fixedAreaServiceImpl;
	
	@Action(value="fixedarea_save",results={@Result(name="success",type="redirect",location="./pages/base/fixed_area.html")})
	public String save(){
		fixedAreaServiceImpl.save(model);
		return SUCCESS;
	}
	@Action(value="fixedarea_findByPage",results={@Result(name="success",type="json")})
	public String findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Specification specification = new Specification<FixedArea>() {

			@Override
			public Predicate toPredicate(Root<FixedArea> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(model.getFixedAreaNum())) {
					Predicate p1 = cb.equal(root.get("fixedAreaNum").as(String.class), model.getFixedAreaNum());
					list.add(p1);
				}
				if (StringUtils.isNotBlank(model.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					list.add(p2);
				}
				
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<FixedArea> page = fixedAreaServiceImpl.findByPage(specification,pageable);
		pushPageDataToValueStack(page);
		return SUCCESS;
	}
	@Action(value="fixedarea_findnoassociation",results={@Result(name="success",type="json")})
	public String fixedarea_findnoassociation(){
		Collection<? extends Customer> customer = WebClient
				.create("http://localhost:9080/crm_management/services/")
				.path("/customerService/findNoAssociationCustomer")
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(customer);
		return SUCCESS;
	}
	@Action(value="fixedarea_findhasassociation",results={@Result(name="success",type="json")})
	public String fixedarea_findhasassociation(){
		Collection<? extends Customer> customer = WebClient
				.create("http://localhost:9080/crm_management/services/")
				.path("/customerService/findHasAssociationCustomer/"+model.getFixedAreaNum())
				.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(customer);
		return SUCCESS;
	}
	private String[] customerIds;
	
	
	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}
	
	@Action(value="fixedArea_associationCustomersToFixedArea",results={@Result(name="success",type="redirect",location="./pages/base/fixed_area.html")})
	public String fixedArea_associationCustomersToFixedArea(){
		String customerIdStr = StringUtils.join(customerIds,",");
		if (StringUtils.isBlank(customerIdStr)) {
			customerIdStr="unexist";
		}
		 WebClient
				.create("http://localhost:9080/crm_management/services/"
						+"/customerService/updataAssocation?fixedAreaNum="+model.getFixedAreaNum()+"&customerIdStr="+customerIdStr)
				.put(null);
		
		return SUCCESS;
	}
	
}
