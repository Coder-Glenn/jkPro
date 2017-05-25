package cn.graduation.web.struts2.baseinfo;

import java.util.List;

import cn.graduation.entity.User;
import cn.graduation.entity.dao.UserDAO;
import cn.graduation.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends _BaseStruts2Action implements ModelDriven<User>{
	private User model = new User();
	
	public User getModel(){
		return model;
	}

	//列表
	public String login(){
		UserDAO oDao = (UserDAO) this.getDao("daoUser");
		Object[] object = new Object[2];
		object[0] = model.getUserName();
		object[1] = model.getPassword();
		List userList = (List)oDao.find("from User o where o.userName = ? and o.password = ?", object);
		if (userList.size() == 0) {
			ActionContext.getContext().put("loginMessage", "用户名或密码错误！");
			return "toIndex";
		}
		return "login";
	}
}
