package impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDaoMapper;
import entity.User;
import service.UserService;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	UserDaoMapper userMapper;
	
	//判断用户登录状态，0为账号或密码错误，1为登录成功，99为未知错误
	@Override
	public int isVaild(String phone, String password){
		Map<String,Object> map = new HashMap<>();
		map.put("phone", phone);
		map.put("password", password);
		User user;
		try {
			user = userMapper.isValid(map);
		} catch (Exception e) {
			e.printStackTrace();
			return 99;
		}
		if (user == null) {
			return 0;  //查询为空
		}
		return 1;
	}

	//插入用户信息，成功为1，失败为0
	@Override
	public int addUser(String phone, String password,  String nick) {
		Map<String,Object> map = new HashMap<>();
		map.put("phone", phone);
		map.put("password", password);
		map.put("nick", nick);
		int result;
		try {
			result = userMapper.addUser(map);
		}catch (Exception e) {
			result = 0;
		}
		return result;
	}
	
	
	
	//根据id获取用户信息
	@Override
	public User getUserById(int id) throws SQLException, IOException {
		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}

	@Override
	public User getUserByPhone(String phone) {
		User user=userMapper.selectByPhone(phone);
		return user;
	}
	
	
	

}
