package cn.graduation.web.struts2.baseinfo.classcode;

import java.util.List;

import cn.graduation.entity.ClassCode;
import cn.graduation.entity.dao.ClassCodeDAO;
import cn.graduation.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ClassCodeAction extends _BaseStruts2Action implements ModelDriven<ClassCode>{
	private ClassCode model = new ClassCode();
	public ClassCode getModel() {
		return model;
	}
	
	//列表
	public String list(){
		ClassCodeDAO oDao = (ClassCodeDAO) this.getDao("daoClassCode");
		List dataList = oDao.find("from ClassCode o");
		ActionContext.getContext().put("dataList", dataList);
		
		return "plist";
	}
	
	//保存
	public String save(){
		ClassCodeDAO oDao = (ClassCodeDAO) this.getDao("daoClassCode");
		oDao.saveOrUpdate(model);
		
		return list();
	}
	
	//删除一条
	public String delete(){
		ClassCodeDAO oDao = (ClassCodeDAO) this.getDao("daoClassCode");
		oDao.delete(model.getId(), ClassCode.class);
		
		return list();
	}
	
	//转向新增页面
	public String tocreate(){
		return "pcreate";
	}
	
	//转向修改页面
	public String toupdate(){
		//准备数据
		ClassCodeDAO oDao = (ClassCodeDAO) this.getDao("daoClassCode");
		ClassCode obj  = (ClassCode) oDao.get(ClassCode.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pupdate";
	}
	
}
