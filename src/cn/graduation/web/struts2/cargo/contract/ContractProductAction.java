package cn.graduation.web.struts2.cargo.contract;

import java.math.BigDecimal;
import java.util.List;

import cn.graduation.entity.ContractProduct;
import cn.graduation.entity.dao.ContractProductDAO;
import cn.graduation.web.common.util.Arith;
import cn.graduation.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ContractProductAction extends _BaseStruts2Action implements ModelDriven<ContractProduct> {
	private ContractProduct model = new ContractProduct();
	public ContractProduct getModel() {
		return model;
	}	
	//转向新增页面
	public String tocreate(){
		//生产厂家下拉框
		ContractProductDAO oDao = (ContractProductDAO) this.getDao("daoContractProduct");
		List factoryList = oDao.find("from Factory o where o.ctype=1");		//1货物的生产厂家
		ActionContext.getContext().put("factoryList", factoryList);
		
		//列表数据
		List dataList = oDao.find("from ContractProduct o where o.contract.id='"+model.getContract().getId()+"'");
		ActionContext.getContext().put("dataList", dataList);
		
		return "pcreate";
	}
	
	//保存
	public String save(){
		ContractProductDAO oDao = (ContractProductDAO) this.getDao("daoContractProduct");
		
		//计算总金额
		Arith arith = new Arith();
		model.setAmount(new BigDecimal(arith.mul(model.getPrice().doubleValue(), model.getCnumber())));
		
		
		oDao.saveOrUpdate(model);
		
		return tocreate();
	}
	
	//删除
	public String delete(){
		ContractProductDAO oDao = (ContractProductDAO) this.getDao("daoContractProduct");
		oDao.delete(model.getId(), ContractProduct.class);
		
		return tocreate();
	}

}
