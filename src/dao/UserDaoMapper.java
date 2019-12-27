package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import entity.Distributer;
import entity.User;

public interface UserDaoMapper {
	//DaoMapper����JDBC�������ݿ�
    
//		//��¼�ж��˺������Ƿ���Ч
		User isValid(Map<String, Object> map) throws SQLException, IOException;
		
		//����û�
		int addUser(Map<String, Object> map);
		
		User selectByPhone(String phone);
		
		//��ѯ�����û�
		List<User> getAllUser()throws SQLException, IOException;
		
		
		//����IDɾ���û�
	    int deleteByPrimaryKey(int id);

	    //����id��ѯ�û�
	    User selectByPrimaryKey(int id);


	   //�޸��û���Ϣ
	  //  int updateByPrimaryKey(User user);
}
