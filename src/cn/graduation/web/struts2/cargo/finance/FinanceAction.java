package cn.graduation.web.struts2.cargo.finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.struts2.ServletActionContext;

import cn.graduation.entity.Export;
import cn.graduation.entity.ExportProduct;
import cn.graduation.entity.ExtEproduct;
import cn.graduation.entity.Finance;
import cn.graduation.entity.PackingList;
import cn.graduation.entity.dao.ExportDAO;
import cn.graduation.entity.dao.FinanceDAO;
import cn.graduation.entity.dao.PackingListDAO;
import cn.graduation.web.common.util.Arith;
import cn.graduation.web.common.util.DownloadBaseAction;
import cn.graduation.web.common.util.UtilFuns;
import cn.graduation.web.common.util.file.FileUtil;
import cn.graduation.web.common.util.file.PioUtil;
import cn.graduation.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ModelDriven;

public class FinanceAction extends _BaseStruts2Action implements ModelDriven<Finance> {
	private Finance model = new Finance();
	public Finance getModel() {
		return model;
	}

	//保存
	public String save() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String id = request.getParameter("id");
		request.setAttribute("id",id);
		
		String subid = request.getParameter("subid");

		String inputBy = request.getParameter("inputBy");
		String inputDate = request.getParameter("inputDate");
		
		PackingListDAO mDao = (PackingListDAO)super.getDao("daoPackingList");
		PackingList packing = (PackingList)mDao.get(PackingList.class, id);
		
		FinanceDAO oDao = (FinanceDAO) this.getDao("daoFinance");
		Finance obj = null;
		if(UtilFuns.isNotEmpty(subid)){
			obj = (Finance)oDao.get(PackingList.class, subid);
		}else{
			obj = new Finance();
			obj.setId(id);		//same as export id
			subid = obj.getId();
		}
		
		
		obj.setInputBy(inputBy);
		if(UtilFuns.isNotEmpty(inputDate)){
			obj.setInputDate(UtilFuns.parseDate(inputDate));
		}
		
		if(UtilFuns.isNotEmpty(packing.getExportIds())){
			String[] exportId = UtilFuns.splitStr(packing.getExportIds(), "|");
			if(UtilFuns.isNotEmpty(exportId)){
				ExportDAO eDao = (ExportDAO)super.getDao("daoExport");
				Export export = null;
				for(int i=0;i<exportId.length;i++){
					export = (Export)eDao.get(Export.class, exportId[i]);
					export.setState(5);		//设置为：财务 tip:利用hibernate自动提交机制，无需显式的保存export对象，即可保存其改变。一定要慎用。
				}
			}
		}
		oDao.saveOrUpdate(obj);
		
		return toedit();
	}
	
	public String toedit() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");		//PackingList id
		request.setAttribute("id",id);
		
		if(UtilFuns.isNotEmpty(id)){
			PackingListDAO mDao = (PackingListDAO)super.getDao("daoPackingList");
			PackingList mobj = (PackingList)mDao.get(PackingList.class, id);
			
			if(mobj==null){
				throw new Exception("请先填写财务信息（制单人、制单日期）, 保存后再点击【财务】!");
			}
			
			Finance obj = mobj.getFinance();
			request.setAttribute("obj",obj);
		}
		
		return "pedit";
	}

	/**
	 * 开发步骤：
	 * 1、建立模板文件
	 * 2、打开模板文件
	 * 3、打开工作簿
	 * 4、获取数据
	 * 5、写入数据
	 * 6、设置格式
	 * 7、关闭文件
	 * 8、下载文件
	 */
	public void print(){
		
	}

}