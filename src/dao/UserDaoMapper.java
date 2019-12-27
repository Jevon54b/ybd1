package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import entity.Distributer;
import entity.User;

public interface UserDaoMapper {
	//DaoMapper用于JDBC访问数据库
    
//		//登录判断账号密码是否有效
		User isValid(Map<String, Object> map) throws SQLException, IOException;
		
		//添加用户
		int addUser(Map<String, Object> map);
		
		User selectByPhone(String phone);
		
		//查询所有用户
		List<User> getAllUser()throws SQLException, IOException;
		
		
		//根据ID删除用户
	    int deleteByPrimaryKey(int id);

	    //根据id查询用户
	    User selectByPrimaryKey(int id);


	   //修改用户信息
	  //  int updateByPrimaryKey(User user);
}
