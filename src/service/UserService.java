package service;

import java.io.IOException;
import java.sql.SQLException;

import entity.User;

public interface UserService {
	
	//判断用户登录状态，0为账号或密码错误，1为账号或密码正确
	int isVaild(String phone,String password);
	
	int addUser(String phone,String password, String nick);
	
	User getUserById(int id)throws SQLException,IOException;
	
	User getUserByPhone(String phone);
}
