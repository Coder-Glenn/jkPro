package cn.graduation.web.struts2.baseinfo.textcode;

import java.util.List;

import cn.graduation.entity.TextCode;
import cn.graduation.entity.dao.ClassCodeDAO;
import cn.graduation.entity.dao.TextCodeDAO;
import cn.graduation.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class TextCodeAction extends _BaseStruts2Action implements ModelDriven<TextCode>{
	private TextCode model = new TextCode();
	public TextCode getModel() {
		return model;
	}
	
	//列表
	public String list(){
		TextCodeDAO oDao = (TextCodeDAO) this.getDao("daoTextCode");
		List dataList = oDao.find("from TextCode o");
		ActionContext.getContext().put("dataList", dataList);
		
		return "plist";
	}
	
	//保存
	public String save(){
		TextCodeDAO oDao = (TextCodeDAO) this.getDao("daoTextCode");
		oDao.saveOrUpdate(model);
		
		return list();
	}
	
	//删除一条
	public String deleteone(){
		TextCodeDAO oDao = (TextCodeDAO) this.getDao("daoTextCode");
		oDao.delete(model.getId(), TextCode.class);
		
		return list();
	}
	
	//删除多条
	public String delete(){
		TextCodeDAO oDao = (TextCodeDAO) this.getDao("daoTextCode");
		String[] ids = model.getId().split(", ");
		oDao.deleteAllById(ids, TextCode.class);
		
		return list();
	}
	
	//转向新增页面
	public String tocreate(){
		//准备数据
		ClassCodeDAO cDao = (ClassCodeDAO) this.getDao("daoClassCode");
		List classCodeList = cDao.find("from ClassCode o");
		ActionContext.getContext().put("classCodeList", classCodeList);
		
		return "pcreate";
	}
	
	//转向修改页面
	public String toupdate(){
		//准备数据
		ClassCodeDAO cDao = (ClassCodeDAO) this.getDao("daoClassCode");
		List classCodeList = cDao.find("from ClassCode o");
		ActionContext.getContext().put("classCodeList", classCodeList);
		
		TextCodeDAO oDao = (TextCodeDAO) this.getDao("daoTextCode");
		TextCode obj  = (TextCode) oDao.get(TextCode.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pupdate";
	}
	
	//转向查看页面
	public String toview(){
		//准备数据
		TextCodeDAO oDao = (TextCodeDAO) this.getDao("daoTextCode");
		TextCode obj  = (TextCode) oDao.get(TextCode.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pview";
	}
	
}
