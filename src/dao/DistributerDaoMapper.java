package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import entity.Distributer;

public interface DistributerDaoMapper {
	//DaoMapper����JDBC�������ݿ�
    
		//��¼�ж��˺������Ƿ���Ч
		Distributer isValid(Map<String, Object> map) throws SQLException, IOException;
		
		//�������
		int addDistributer(Map<String, Object> map);
		
		Distributer selectByPhone(String phone);
		
		//��ѯ�����û�
		List<Distributer> getAllDistributer()throws SQLException, IOException;
		
		
		//����IDɾ���û�
	    int deleteByPrimaryKey(int id);

	    //����id��ѯ�û�
	    Distributer selectByPrimaryKey(int id);


	   //�޸��û���Ϣ
	  //  int updateByPrimaryKey(User user);
}
