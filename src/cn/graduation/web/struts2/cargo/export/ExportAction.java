package cn.graduation.web.struts2.cargo.export;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.graduation.entity.ContractProduct;
import cn.graduation.entity.Export;
import cn.graduation.entity.ExportProduct;
import cn.graduation.entity.ExtCproduct;
import cn.graduation.entity.ExtEproduct;
import cn.graduation.entity.dao.ContractProductDAO;
import cn.graduation.entity.dao.ExportDAO;
import cn.graduation.entity.dao.ExportProductDAO;
import cn.graduation.web.common.util.UtilFuns;
import cn.graduation.web.print.ExportPrint;
import cn.graduation.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ExportAction extends _BaseStruts2Action implements ModelDriven<Export> {
	private Export model = new Export();

	public Export getModel() {
		return model;
	}

	// 列表
	public String list() {
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		List dataList = oDao.find("from Export o");
		ActionContext.getContext().put("dataList", dataList);

		return "plist";
	}
	
	//保存
	public String save(){
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		if(model.getId()==null){
			model.setState(0);			//0草稿 1已上报待报运
		}
		
		//批量保存ExportProduct
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] mr_id = request.getParameterValues("mr_id");
		String[] mr_changed = request.getParameterValues("mr_changed");
		String[] ep_productNo = request.getParameterValues("ep_productNo");
		String[] ep_cnumber = request.getParameterValues("ep_cnumber");
		String[] ep_grossWeight = request.getParameterValues("ep_grossWeight");
		String[] ep_netWeight = request.getParameterValues("ep_netWeight");
		
		Set eSet = new HashSet();
		ExportProductDAO eDao = (ExportProductDAO)this.getDao("daoExportProduct");
		ExportProduct obj = null;
		int j=0;
		for(int i=0;i<ep_productNo.length;i++){
			if(mr_changed[i]!=null && !mr_changed[i].equals("")){
				obj = (ExportProduct) oDao.get(ExportProduct.class, mr_id[i]);
				obj.setProductNo(ep_productNo[i]);
				if(ep_cnumber[i]!=null && !ep_cnumber[i].equals("")){
					obj.setCnumber(Integer.parseInt(ep_cnumber[i]));
				}
				if(ep_grossWeight[i]!=null && !ep_grossWeight[i].equals("")){
					obj.setGrossWeight(new BigDecimal(ep_grossWeight[i]));
				}
				if(ep_netWeight[i]!=null && !ep_netWeight[i].equals("")){
					obj.setNetWeight(new BigDecimal(ep_netWeight[i]));
				}
				eSet.add(obj);
			}
		}
		eDao.saveOrUpdateAll(eSet);		//批量提交
		
		
		
		oDao.saveOrUpdate(model);
		
		return list();

	}
	

	//复制选中的多个合同中的货物，附件信息，加入到新的报运中
	public String contractsave() throws Exception{
		/*
		 * step:
		 * 1.得到选择的id数组
		 * 2.得到合同对象、取它下面的货物、取货物下的附件
		 * 3.保存
		 */
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String contractIds = "";
		String customerContract = "";
		
		UtilFuns utilFuns = new UtilFuns();
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		
		
		
		Export export = new Export();
		String[] selContractIds = request.getParameterValues("id");
		
		String selIds = utilFuns.joinInStr(selContractIds);		//构造子查询串
		//查到多个合同下的所有货物
		String _tmpstr = oDao.findString("select o.id from ContractProduct o where o.contract.id in ("+selIds+")");
		
		String[] contractProductIds = utilFuns.splitStr(_tmpstr, ",");
		
		
		ContractProductDAO cpDao = (ContractProductDAO) this.getDao("daoContractProduct");
		ContractProduct contractProduct = null;
		
		
		

		BigDecimal bTotal = new BigDecimal(0);
		
		//复制选择的合同货物信息到报运货物，及附件，以及修改主表相应信息 
		
			ExportProductDAO epDao = (ExportProductDAO) this.getDao("daoExportProduct");
			ExportProduct eProduct = null;
			Set<ExportProduct> exportProductSet = new HashSet<ExportProduct>();
			
			Set extEproductSet = new HashSet();
			ExtCproduct extcproduct = null;
			ExtEproduct eproduct = null;
			int _rate = 0;
			
			for(int h = 0; h < contractProductIds.length; h++){
				eProduct = new ExportProduct();
				contractProduct = (ContractProduct)cpDao.get(ContractProduct.class, contractProductIds[h]);
				eProduct.setExport(export);
				
				if(contractProduct!=null && contractProduct.getContract()!=null){
					if(customerContract.indexOf(contractProduct.getContract().getContractNo()+",")==-1){	//排除已经在串中的
						customerContract += contractProduct.getContract().getContractNo() +",";				//实现合同号的拼接
					}
					if(contractIds.indexOf(contractProduct.getContract().getId()+",")==-1){					//排除已经在串中的
						contractIds += contractProduct.getContract().getId() +",";							//实现合同ID的拼接
					}
				}
				
				
				eProduct.setContractProductId(contractProductIds[h]);
				eProduct.setContractId(contractProduct.getContract().getId());
				eProduct.setContractNo(contractProduct.getContract().getContractNo());
				
				eProduct.setProductNo(contractProduct.getProductNo());
				eProduct.setProductImage(contractProduct.getProductImage());
				eProduct.setProductDesc(contractProduct.getProductDesc());
				eProduct.setFactory(contractProduct.getFactory());
				eProduct.setLoadingRate(contractProduct.getLoadingRate());
				eProduct.setPackingUnit(contractProduct.getPackingUnit());
				eProduct.setOutNumber(contractProduct.getOutNumber());
				
				//合同货物的走货状态
				if(contractProduct.getOutNumber()>0){
					eProduct.setCnumber(contractProduct.getCnumber()-contractProduct.getOutNumber());		//如果出过货，则界面显示剩余的货
					if(contractProduct.getLoadingRate()!=null && contractProduct.getCnumber()!=null){
						_rate = Integer.parseInt(contractProduct.getLoadingRate().substring(contractProduct.getLoadingRate().indexOf("/")+1));
						eProduct.setBoxNum(eProduct.getCnumber()/_rate);										//箱数=数量/装率的分母
					}
				}else{
					eProduct.setCnumber(contractProduct.getCnumber());
					eProduct.setBoxNum(contractProduct.getBoxNum());
				}
				contractProduct.setOutNumber(contractProduct.getCnumber());
				contractProduct.setFinished(true);	//默认全部出货
				
				eProduct.setGrossWeight(contractProduct.getGrossWeight());
				eProduct.setNetWeight(contractProduct.getNetWeight());
				eProduct.setSizeLength(contractProduct.getSizeLength());
				eProduct.setSizeWidth(contractProduct.getSizeWidth());
				eProduct.setSizeHeight(contractProduct.getSizeHeight());
				eProduct.setExPrice(contractProduct.getExPrice());
				eProduct.setTax(contractProduct.getPrice());		//收购单价.含税=合同单价
				eProduct.setAmount(contractProduct.getAmount());
				
				eProduct.setOrderNo(h+1);	//根据list的顺序排序
				
				for(Iterator<ExtCproduct> it = contractProduct.getExtCproduct().iterator(); it.hasNext(); ){
					eproduct = new ExtEproduct();
					extcproduct = it.next();
					
					eproduct.setAccessories(extcproduct.isAccessories());
					eproduct.setAmount(extcproduct.getAmount());
					eproduct.setBoxNum(extcproduct.getBoxNum());
					eproduct.setCnumber(extcproduct.getCnumber());
					eproduct.setCostPrice(extcproduct.getCostPrice());
					eproduct.setCtype(extcproduct.getCtype());
					eproduct.setCunit(extcproduct.getCunit());
					eproduct.setExPrice(extcproduct.getExPrice());
					eproduct.setExUnit(extcproduct.getExUnit());
					eproduct.setFactory(extcproduct.getFactory());
					eproduct.setFinished(extcproduct.isFinished());
					eproduct.setGrossWeight(extcproduct.getGrossWeight());
					eproduct.setLoadingRate(extcproduct.getLoadingRate());
					eproduct.setNetWeight(extcproduct.getNetWeight());
					eproduct.setNoTax(extcproduct.getNoTax());
					eproduct.setOutNumber(extcproduct.getOutNumber());
					eproduct.setPackingUnit(extcproduct.getPackingUnit());
					eproduct.setPrice(extcproduct.getPrice());
					eproduct.setProductDesc(extcproduct.getProductDesc());
					eproduct.setProductImage(extcproduct.getProductImage());
					eproduct.setProductName(extcproduct.getProductName());
					eproduct.setProductNo(extcproduct.getProductNo());
					eproduct.setProductRequest(extcproduct.getProductRequest());
					eproduct.setSizeHeight(extcproduct.getSizeHeight());
					eproduct.setSizeLength(extcproduct.getSizeLength());
					eproduct.setSizeWidth(extcproduct.getSizeWidth());
					eproduct.setExPrice(extcproduct.getExPrice());
					eproduct.setTax(extcproduct.getTax());
					
					eproduct.setOrderNo(extcproduct.getOrderNo());
					
					eproduct.setExportProduct(eProduct);
					extEproductSet.add(eproduct);
				}
				
				eProduct.setExtEproduct(extEproductSet);
				
				exportProductSet.add(eProduct);
			}
			
			export.setContractIds(utilFuns.DelLastChar(contractIds));
			export.setCustomerContract(utilFuns.DelLastChar(customerContract));
			export.setExportProduct(exportProductSet);


			oDao.saveOrUpdate(export);


		return list();		
	}
	
	// 转向修改页面
	public String toupdate() {
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		Export obj = (Export) oDao.get(Export.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		//拼接html串
		StringBuffer sBuf = new StringBuffer();
		//addTRRecord(objId, id, productNo, cnumber, grossWeight, netWeight)
		for(ExportProduct ep:obj.getExportProduct()){
			sBuf.append("addTRRecord('resultTable', ");
			sBuf.append("'").append(ep.getId()).append("',");
			sBuf.append("'").append(UtilFuns.convertNull(ep.getProductNo())).append("',");
			sBuf.append("'").append(UtilFuns.convertNull(ep.getCnumber())).append("',");
			sBuf.append("'").append(UtilFuns.convertNull(ep.getGrossWeight())).append("',");
			sBuf.append("'").append(UtilFuns.convertNull(ep.getNetWeight())).append("'");
			sBuf.append(");");
		}
		
		ActionContext.getContext().put("mrecordData", sBuf.toString());
		return "pupdate";
	}
	
	// 转向新增页面
	public String toview() {
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		Export obj = (Export) oDao.get(Export.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pview";
	}
	
	// 转向新增页面
	public String toviewinfo() {
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		Export obj = (Export) oDao.get(Export.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pviewinfo";
	}
	
	//删除
	public String delete(){
		String[] ids = model.getId().split(", ");
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		oDao.deleteAllById(ids, Export.class);
		
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
			ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
			Export obj = null;
			for(int i=0;i<ids.length;i++){
				obj = (Export) oDao.get(Export.class, ids[i]);
				obj.setState(curValue);
				oSet.add(obj);
			}
			oDao.saveOrUpdateAll(oSet);
		}
	}	
	
	//复制
	public String copy(){
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		Export oldExport = (Export) oDao.get(Export.class, model.getId());
		oldExport.setId(null);
		
//		oldExport.setExportNo("[<font color='red'>复制</font>]" + oldExport.getExportNo());
		
		//初始化
		oldExport.setState(0);
		
//		//货物
//		for(ExportProduct cp: oldExport.getExportProduct()){
//			cp.setId(null);
//			//附件
//			for(ExtCproduct ep: cp.getExtEproduct()){
//				ep.setId(null);
//			}
//		}
		
		
		oDao.saveOrUpdate(oldExport);
		
		return list();
	}
	
	
	//打印
	public void print() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		ExportDAO oDao = (ExportDAO)this.getDao("daoExport");
		
		ExportPrint cp  = new ExportPrint();
		cp.print(request, response, oDao);

	}
	
}
