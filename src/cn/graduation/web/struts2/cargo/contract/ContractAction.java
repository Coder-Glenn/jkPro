package cn.graduation.web.struts2.cargo.contract;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.graduation.entity.Contract;
import cn.graduation.entity.ContractProduct;
import cn.graduation.entity.ExtCproduct;
import cn.graduation.entity.dao.ContractDAO;
import cn.graduation.entity.dao.ContractProductDAO;
import cn.graduation.web.print.ContractPrint;
import cn.graduation.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ContractAction extends _BaseStruts2Action implements
		ModelDriven<Contract> {
	private Contract model = new Contract();

	public Contract getModel() {
		return model;
	}

	// 列表
	public String list() {
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		List dataList = oDao.find("from Contract o");
		ActionContext.getContext().put("dataList", dataList);

		return "plist";
	}
	
	//保存
	public String save(){
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		if(model.getId()==null){
			model.setState(0);			//0草稿 1已上报待报运
		}
		oDao.saveOrUpdate(model);
		
		return list();
	}
	

	// 转向新增页面
	public String tocreate() {
		return "pcreate";
	}
	
	// 转向修改页面
	public String toupdate() {
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		Contract obj = (Contract) oDao.get(Contract.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pupdate";
	}
	
	// 转向新增页面
	public String toview() {
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		Contract obj = (Contract) oDao.get(Contract.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pview";
	}
	
	//删除
	public String delete(){
		String[] ids = model.getId().split(", ");
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		oDao.deleteAllById(ids, Contract.class);
		
		return list();
	}

	//上报
	public String submit(){
		this.state(1);		
		return list();
	}
	//取消
	public String cancelsubmit(){
		this.state(0);		
		return list();
	}
	
	private void state(Integer curValue){
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameterValues("id");
		
		if(ids!=null && ids.length>0){
			Set oSet = new HashSet();
			ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
			Contract obj = null;
			for(int i=0;i<ids.length;i++){
				obj = (Contract) oDao.get(Contract.class, ids[i]);
				obj.setState(curValue);
				oSet.add(obj);
			}
			oDao.saveOrUpdateAll(oSet);
		}
	}	
	
	//复制
	public String copy(){
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		Contract oldContract = (Contract) oDao.get(Contract.class, model.getId());
		oldContract.setId(null);
		
		oldContract.setContractNo("[<font color='red'>复制</font>]" + oldContract.getContractNo());
		
		//初始化
		oldContract.setState(0);
		
		//货物
		for(ContractProduct cp: oldContract.getContractProduct()){
			cp.setId(null);
			//附件
			for(ExtCproduct ep: cp.getExtCproduct()){
				ep.setId(null);
			}
		}
		
		
		oDao.saveOrUpdate(oldContract);
		
		return list();
	}
	
	
	//打印
	public void print() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		
		ContractProductDAO oDao = (ContractProductDAO)this.getDao("daoContractProduct");
		ContractDAO pDao = (ContractDAO)this.getDao("daoContract");
		
		ContractPrint cp  = new ContractPrint();
		cp.print(model.getId(), response, oDao, pDao);

	}
	
}
