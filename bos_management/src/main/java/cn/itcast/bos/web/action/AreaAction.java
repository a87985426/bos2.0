package cn.itcast.bos.web.action;

import java.io.File;




import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.IAreaService;
import cn.itcast.bos.utils.PinYin4jUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("protopyte")
@Namespace("/")
@ParentPackage("json-default")
public class AreaAction extends BaseAction<Area>{

	@Autowired
	private IAreaService areaServiceImpl;
	
	private File file;
	public void setFile(File file) {
		this.file = file;
	}
	@Action(value="area_page",results={@Result(name="success",type="json")})
	public String findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Specification specification = new Specification<Area>() {

			@Override
			public Predicate toPredicate(Root<Area> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNotBlank(model.getProvince())) {
					Predicate p1 = cb.like(root.get("province").as(String.class), "%"+model.getProvince()+"%");
					list.add(p1);
				}
				if (StringUtils.isNotBlank(model.getCity())) {
					Predicate p2 = cb.like(root.get("city").as(String.class), "%"+model.getCity()+"%");
					list.add(p2);
				}
				if (StringUtils.isNotBlank(model.getDistrict())) {
					Predicate p3 = cb.like(root.get("district").as(String.class), "%"+model.getDistrict()+"%");
					list.add(p3);
				}
				
				return cb.and(list.toArray(new Predicate[0]));
			}

		
		};
		Page<Area> page = areaServiceImpl.findByPage(specification,pageable);
		this.pushPageDataToValueStack(page);
		return SUCCESS;
	}
	
	
	
	@Action(value="area_import")
	public String batchImport() throws IOException{
		List<Area> list = new ArrayList<Area>();
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		for (Row row : hssfSheet) {
			if (row.getRowNum()==0) {
				continue;
			}
			//空行或者单元格内都是空格
			if (row.getCell(0)==null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
				continue;
			}
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district=row.getCell(3).getStringCellValue();
			province = province.substring(0, province.length()-1);
			city = city.substring(0, city.length()-1);
			district = district.substring(0, district.length()-1);
			
			StringBuffer sb = new StringBuffer();
			if (province.equals(city)) {
				String[] stringToPinyin = PinYin4jUtils.getHeadByString(province+district);
				for (String s : stringToPinyin) {
					sb.append(s);
				}
			}else{
				String[] stringToPinyin = PinYin4jUtils.getHeadByString(province+city+district);
				for (String s : stringToPinyin) {
					sb.append(s);
				}
			}
			String shortcode = sb.toString();
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			Area area = new Area();
			area.setId(row.getCell(0).getStringCellValue());
			area.setProvince(row.getCell(1).getStringCellValue());
			area.setCity(row.getCell(2).getStringCellValue());
			area.setDistrict(row.getCell(3).getStringCellValue());
			area.setPostcode(row.getCell(4).getStringCellValue());
			area.setShortcode(shortcode);
			area.setCitycode(citycode);
			list.add(area);
			
		}
		areaServiceImpl.batchSave(list);
		return NONE;
	}
}
