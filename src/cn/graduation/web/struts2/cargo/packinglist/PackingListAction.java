package cn.graduation.web.struts2.cargo.packinglist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.graduation.entity.Export;
import cn.graduation.entity.PackingList;
import cn.graduation.entity.dao.ExportDAO;
import cn.graduation.entity.dao.PackingListDAO;
import cn.graduation.web.common.util.UtilFuns;
import cn.graduation.web.print.PackingListPrint;
import cn.graduation.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class PackingListAction extends _BaseStruts2Action implements ModelDriven<PackingList> {
	private PackingList model = new PackingList();

	public PackingList getModel() {
		return model;
	}

	// 列表
	public String list() {
		PackingListDAO oDao = (PackingListDAO) this.getDao("daoPackingList");
		List dataList = oDao.find("from PackingList o");
		ActionContext.getContext().put("dataList", dataList);

		return "plist";
	}
	
	//保存
	public String save(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String[] exportIds = request.getParameterValues("exportIds");			//id|no
		String _exportIds = "";
		String _exportNos = "";
		
		for(int i=0;i<exportIds.length;i++){
			
			String[] _temp = exportIds[i].split("\\|");			//以竖杠隔开，竖杠为正则关键字需转义处理\\|
			_exportIds += _temp[0] + "|";
			_exportNos += _temp[1] + "|";
		}
		_exportIds = UtilFuns.DelLastChar(_exportIds);
		_exportNos = UtilFuns.DelLastChar(_exportNos);
		

		//保存管理的多个报运号及其ID
		model.setExportIds(_exportIds);
		model.setExportNos(_exportNos);
		
		
		PackingListDAO oDao = (PackingListDAO) this.getDao("daoPackingList");
		
		//改变报运的状态
		String[] expIds = _exportIds.split("\\|");
		
		ExportDAO eDao = (ExportDAO) this.getDao("daoExport");
		Export export;
		Set oSet = new HashSet();
		for(int i=0;i<expIds.length;i++){
			export = (Export) eDao.get(Export.class, expIds[i]);
			export.setState(2);			//0-草稿 1-已上报 2-装箱 3-委托 4-发票 5-财务
			oSet.add(export);
		}
		eDao.saveOrUpdateAll(oSet);
		
		oDao.saveOrUpdate(model);
		
		return list();

	}
	
	//从报运业务新增
	public String tocreate(){
		ExportDAO eDao = (ExportDAO) this.getDao("daoExport");
		String[] ids = model.getId().split(", ");			//export id
		
		
		//拼接HMTL代码片段
		StringBuffer sBuf = new StringBuffer();
		Export export;
		for(int i=0;i<ids.length;i++){
			export = (Export) eDao.get(Export.class, ids[i]);
			sBuf.append("<input type='checkbox' name='exportIds' checked value='").append(ids[i]).append("|").append(export.getCustomerContract()).append("' class='input'>");
			sBuf.append(export.getCustomerContract());
			sBuf.append("&nbsp;&nbsp;");
		}
		ActionContext.getContext().put("mrecordData", sBuf.toString());		//存储
		
		return "pcreate";
	}
	

	
	// 转向修改页面
	public String toupdate() {
		PackingListDAO oDao = (PackingListDAO) this.getDao("daoPackingList");
		PackingList obj = (PackingList) oDao.get(PackingList.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		//拼接HMTL代码片段
		StringBuffer sBuf = new StringBuffer();
		String[] _epIds = obj.getExportIds().split("\\|");
		String[] _epNos = obj.getExportNos().split("\\|");
		
		for(int i=0;i<_epIds.length;i++){
			sBuf.append("<input type='checkbox' name='exportIds' checked value='").append(_epIds[i]).append("|").append(_epNos[i]).append("' class='input'>");
			sBuf.append(_epNos[i]);
			sBuf.append("&nbsp;&nbsp;");
		}
		ActionContext.getContext().put("mrecordData", sBuf.toString());		//存储

		return "pupdate";
	}
	
	// 转向新增页面
	public String toview() {
		PackingListDAO oDao = (PackingListDAO) this.getDao("daoPackingList");
		PackingList obj = (PackingList) oDao.get(PackingList.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		
		//拼接HMTL代码片段
		StringBuffer sBuf = new StringBuffer();
		String[] _epIds = obj.getExportIds().split("\\|");
		String[] _epNos = obj.getExportNos().split("\\|");
		
		for(int i=0;i<_epIds.length;i++){
			
			sBuf.append("<a href='/export/exportAction_toviewinfo?id=").append(_epIds[i]).append("'>");
			sBuf.append(_epNos[i]);
			sBuf.append("</a>");
			sBuf.append("&nbsp;&nbsp;");
		}
		ActionContext.getContext().put("mrecordData", sBuf.toString());		//存储		
		
		return "pview";
	}
	
	//删除
	public String delete(){
		String[] ids = model.getId().split(", ");
		PackingListDAO oDao = (PackingListDAO) this.getDao("daoPackingList");
		oDao.deleteAllById(ids, PackingList.class);
		
		return list();
	}


	
	//打印
	public void print() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		PackingListDAO oDao = (PackingListDAO)this.getDao("daoPackingList");
		ExportDAO eDao = (ExportDAO)this.getDao("daoExport");
		
		PackingListPrint cp  = new PackingListPrint();
		cp.print(request, response, oDao, eDao);

	}
	
}
