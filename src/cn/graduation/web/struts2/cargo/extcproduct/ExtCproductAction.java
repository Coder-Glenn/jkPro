package cn.graduation.web.struts2.cargo.extcproduct;

import java.util.List;

import cn.graduation.entity.ExtCproduct;
import cn.graduation.entity.dao.ExtCproductDAO;
import cn.graduation.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ExtCproductAction extends _BaseStruts2Action implements ModelDriven<ExtCproduct> {
	private ExtCproduct model = new ExtCproduct();
	public ExtCproduct getModel() {
		return model;
	}	
	
	//转向新增页面
	public String tocreate(){
		//生产厂家下拉框
		ExtCproductDAO oDao = (ExtCproductDAO) this.getDao("daoExtCproduct");
		List factoryList = oDao.find("from Factory o where o.ctype=2");		//2附件生产厂家
		ActionContext.getContext().put("factoryList", factoryList);
		
		//列表数据
		List dataList = oDao.find("from ExtCproduct o where o.contractProduct.id='"+model.getContractProduct().getId()+"'");
		ActionContext.getContext().put("dataList", dataList);
		
		return "pcreate";
	}
	
	//保存
	public String save(){
		ExtCproductDAO oDao = (ExtCproductDAO) this.getDao("daoExtCproduct");
		oDao.saveOrUpdate(model);
		
		return tocreate();
	}
	
	//删除
	public String delete(){
		ExtCproductDAO oDao = (ExtCproductDAO) this.getDao("daoExtCproduct");
		oDao.delete(model.getId(), ExtCproduct.class);
		
		return tocreate();
	}
}
