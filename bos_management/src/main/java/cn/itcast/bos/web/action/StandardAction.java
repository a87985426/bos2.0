package cn.itcast.bos.web.action;





import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.IStandardService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class StandardAction  extends ActionSupport implements ModelDriven<Standard>{
	private Standard standard = new Standard();
	@Override
	public Standard getModel() {
		// TODO Auto-generated method stub
		return standard;
	}
	@Autowired
	private IStandardService standardserviceimpl;
	@Action(value="standard_save",results={@Result(name="success",type="redirect",location="./pages/base/standard.html")})
	public String save(){
		System.out.println("保存standard!");
		standardserviceimpl.save(standard);
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
	@Action(value="standard_page",results={@Result(name="success",type="json")})
	public String findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> page=  standardserviceimpl.findByPage(pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
	@Action(value="standard_findAll",results={@Result(name="success",type="json")})
	public String findAll(){
		List<Standard> list = standardserviceimpl.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	
}
