package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import com.mysql.cj.util.StringUtils;

import entity.ResponseCode;
import entity.ServerResponse;
import service.DistributerService;

@RequestMapping(value="/distributer/")
@Controller
public class DistributerController {

	@Autowired
	DistributerService distService;
	
	@ResponseBody
	@RequestMapping(value="register.do",method=RequestMethod.POST)
	public ServerResponse registerDistributer(HttpServletRequest rs) {
		String phone = rs.getParameter("phone");
		String name = rs.getParameter("name");
		String password = rs.getParameter("password");
		String id_no = rs.getParameter("id_no");
		if (StringUtils.isNullOrEmpty(phone)) {
			return ServerResponse.createByErrorMessage("�ֻ��Ų���Ϊ��");
		}
		if (StringUtils.isNullOrEmpty(password)) {
			return ServerResponse.createByErrorMessage("���벻��Ϊ��");
		}
		if (StringUtils.isNullOrEmpty(name)) {
			return ServerResponse.createByErrorMessage("��������Ϊ��");
		}
		if (StringUtils.isNullOrEmpty(id_no)) {
			return ServerResponse.createByErrorMessage("���֤�Ų���Ϊ��");
		}
		if(id_no.length()!=18) {
			return ServerResponse.createByErrorMessage("���֤�����ʽ����");
		}
		
		int result = distService.addDistributer(phone, password, name, id_no);
		
		if (result == 1) {
			return ServerResponse.createBySuccessMessage("ע��ɹ�");
		}else {
			return ServerResponse.createByErrorMessage("ע��ʧ��");
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	public ServerResponse loginDistributer(HttpServletRequest rs) {
		String phone = rs.getParameter("phone");
		String password = rs.getParameter("password");
		if (StringUtils.isNullOrEmpty(phone)) {
			return ServerResponse.createByErrorMessage("�ֻ��Ų���Ϊ��");
		}
		if (StringUtils.isNullOrEmpty(password)) {
			return ServerResponse.createByErrorMessage("���벻��Ϊ��");
		}
		int result = distService.isVaild(phone, password);
		switch (result) {
		case 0:
			return ServerResponse.createByErrorMessage("�ֻ��Ż��������");
		case 1:
			return ServerResponse.createByErrorMessage("�˺����������");
		case 2:
			return ServerResponse.createBySuccess("��¼�ɹ�", distService.getDistByPhone(phone));
		case -1:
			return ServerResponse.createByErrorMessage("�˺�δͨ�����");
		default:
			return ServerResponse.createByErrorMessage("δ֪����");
		}
		
		
		
	}
}
