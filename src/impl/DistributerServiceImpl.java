package impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.DistributerDaoMapper;
import entity.Distributer;
import service.DistributerService;

@Service
public class DistributerServiceImpl implements DistributerService {


	@Autowired
	DistributerDaoMapper distMapper;
	
	//判断骑手登录状态，0为账号或密码错误，1为等待审核，2为通过审核，-1为审核不通过
	@Override
	public int isVaild(String phone, String password){
		Map<String,Object> map = new HashMap<>();
		map.put("phone", phone);
		map.put("password", password);
		Distributer distributer;
		try {
			distributer = distMapper.isValid(map);
		} catch (Exception e) {
			e.printStackTrace();
			return 99;
		}
		if (distributer == null) {
			return 0;  //查询为空
		}
		switch (distributer.getIs_pass()) {
		case 0:
			return 1;
		case 1:
			return 2;
		case -1:
			return -1;
		default:
			return 0;
		}
		
	}

	//插入骑手信息，成功为1，失败为0
	@Override
	public int addDistributer(String phone, String password, String name, String id_no) {
		Map<String,Object> map = new HashMap<>();
		map.put("phone", phone);
		map.put("password", password);
		map.put("name", name);
		map.put("id_no", id_no);
		int result;
		try {
			result = distMapper.addDistributer(map);
		}catch (Exception e) {
			result = 0;
		}
		
		return result;
	}
	
	
	
	//根据id获取骑手信息
	@Override
	public Distributer getDistById(int id) throws SQLException, IOException {
		Distributer distributer = distMapper.selectByPrimaryKey(id);
		return distributer;
	}

	@Override
	public Distributer getDistByPhone(String phone) {
		Distributer distributer=distMapper.selectByPhone(phone);
		return distributer;
	}
	
	
	

}
