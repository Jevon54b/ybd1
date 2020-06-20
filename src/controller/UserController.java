package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mysql.cj.util.StringUtils;

import entity.ResponseCode;
import entity.ServerResponse;
import service.UserService;

@RequestMapping(value="/user/")
@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@ResponseBody
	@RequestMapping(value="register.do",method=RequestMethod.POST)
	public ServerResponse registerUser(HttpServletRequest rs) {
		String phone = rs.getParameter("phone");
		String nick = rs.getParameter("nick");
		String password = rs.getParameter("password");
		if (StringUtils.isNullOrEmpty(phone)) {
			return ServerResponse.createByErrorMessage("�ֻ��Ų���Ϊ��");
		}
		if (StringUtils.isNullOrEmpty(password)) {
			return ServerResponse.createByErrorMessage("���벻��Ϊ��");
		}
		if (StringUtils.isNullOrEmpty(nick)) {
			return ServerResponse.createByErrorMessage("�ǳƲ���Ϊ��");
		}
		
		int result = userService.addUser(phone, password, nick);
		
		if (result == 1) {
			return ServerResponse.createBySuccessMessage("ע��ɹ�");
		}else {
			return ServerResponse.createByErrorMessage("ע��ʧ��");
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	public ServerResponse loginUser(HttpServletRequest rs) {
		String phone = rs.getParameter("phone");
		String password = rs.getParameter("password");
		if (StringUtils.isNullOrEmpty(phone)) {
			return ServerResponse.createByErrorMessage("�ֻ��Ų���Ϊ��");
		}
		if (StringUtils.isNullOrEmpty(password)) {
			return ServerResponse.createByErrorMessage("���벻��Ϊ��");
		}
		int result = userService.isVaild(phone, password);
		switch (result) {
		case 0:
			return ServerResponse.createByErrorMessage("�ֻ��Ż��������");
		case 1:
			return ServerResponse.createBySuccess("��¼�ɹ�", userService.getUserByPhone(phone));
		default:
			return ServerResponse.createByErrorMessage("δ֪����");
		}
	}
}
