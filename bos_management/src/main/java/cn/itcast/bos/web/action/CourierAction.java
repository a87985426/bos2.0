package cn.itcast.bos.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Set;
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

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.ICourierService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("protopyte")
@Namespace("/")
@ParentPackage("json-default")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {
	private Courier courier = new Courier();
	
	@Override
	public Courier getModel() {
		return courier;
	}
	@Autowired
	private ICourierService courierServiceImpl;
	@Action(value="courier_save",results={@Result(name="success",type="redirect",location="./pages/base/courier.html")})
	public String save(){
		System.out.println("保存courier!");
		courierServiceImpl.save(courier);
		return SUCCESS;
	}
	private Integer page;
	private Integer rows;
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	@Action(value="courier_page",results={@Result(name="success",type="json")})
	public String findByPage(){
		Specification<Courier> specification = new Specification<Courier>() {
			/**
			 * CriteriaBuilder  相当与构造条件类型  and  equal  like 等
			 * root  相当与 条件的值  后面跟着属性名  等
			 */
			@Override
			public Predicate toPredicate(Root<Courier> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if (StringUtils.isNotBlank(courier.getCourierNum())) {
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
					list.add(p1);
				}
				if (StringUtils.isNotBlank(courier.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), "%"+courier.getCourierNum()+"%");
					list.add(p2);
				}
				if (StringUtils.isNotBlank(courier.getType())) {
					Predicate p3 = cb.equal(root.get("type").as(String.class), courier.getType());
					list.add(p3);
				}
				//多表查询
				Join<Object, Object> standardRoot = root.join("standard",JoinType.INNER);
				if (courier.getStandard()!= null && StringUtils.isNotBlank(courier.getStandard().getName())) {
						Predicate p4 = cb.like(standardRoot.get("name").as(String.class), courier.getStandard().getName());
						list.add(p4);
				}
				//将所有条件连接返回
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Courier> page=  courierServiceImpl.findByPage(specification,pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action(value="courier_delete",results={@Result(name="success",type="redirect",location="/pages/base/courier.html")})
	public String delete(){
		String[] split = ids.split(":");
		
		courierServiceImpl.delete(split);
		return SUCCESS;
		
	}
	@Action(value="courier_findnoassociation",results={@Result(name="success",type="json")})
	public String findNoAssocation(){
		
		List<Courier> list = courierServiceImpl.findNoAssocation();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
		
	}
}
