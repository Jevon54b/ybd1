package impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jni.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import dao.OrderDaoMapper;
import entity.MedInOrder;
import entity.Order;
import entity.OrderDetail;
import entity.OrderToDist;
import service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDaoMapper odm;

	// 0Ϊʧ�ܣ�1Ϊ�ɹ�
	@Override
	public int addMedToCurOrder(int med_id, int user_id) {
		Order curOrder;
		int order_id;
		Integer om_id;
		Float sum;
		Map<String, Object> map;
		curOrder = odm.getCurrentOrderByUserId(user_id);
		if (curOrder == null) {
			try {
				odm.createNewCurOrder(user_id);
			} catch (Exception e) {
				System.out.println("�����¶���ʧ��");
				e.printStackTrace();
				return 0;
			}
			curOrder = odm.getCurrentOrderByUserId(user_id);
			order_id = curOrder.getId();
			map = new HashMap<>();
			map.put("order_id", order_id);
			map.put("med_id", med_id);
			try {
				odm.addMedToOrder(map);
			} catch (Exception e) {
				System.out.println("���ҩƷ�����ﳵʧ��");
				e.printStackTrace();
				return 0;
			}
		} else {
			order_id = curOrder.getId();
			map = new HashMap<>();
			map.put("med_id", med_id);
			map.put("order_id", order_id);
			om_id = odm.matchOrderMed(map);

			if (om_id == null) {
				// ��ҩƷ��δ���붩��
				try {
					odm.addMedToOrder(map);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("���ҩƷ������ʧ��");
					return 0;
				}
			} else {
				// ��ҩƷ�Ѵ��ڶ����� ��������
				try {
					odm.addMednum(om_id);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("����ҩƷ����ʧ��");
					return 0;
				}
			}

		}
		// ���¶����ܶ�
		sum = odm.getOrderTotalSum(order_id);
		System.out.print("�����ܺ�sum:" + sum);
		try {
			map = new HashMap<>();
			map.put("total_sum", sum);
			map.put("order_id", order_id);
			odm.updateSumToOrder(map);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���¶����ܶ�ʧ��");
			return 0;
		}
		return 1;

	}

	// ��ȡ��ǰ������ҩƷ
	@Override
	public List<MedInOrder> getMedListFromCurOrder(int user_id) {
		List<MedInOrder> list;
		try {
			list = odm.getMedListFromCurOrder(user_id);
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<>();
		}
		return list;
	}

	// ��ҩƷ������1 �����Ϊ0��ɾ�������е�ҩƷ
	// �ɹ�����1��ʧ�ܷ���0 ,-1Ϊ���¶����ܶ�ʧ��
	@Override
	public int subMednumInOrder(int om_id,int user_id) {
		
		Integer med_num = odm.getCurMednumByOMId(om_id);
		
		if (med_num > 1) {
			try {
				odm.subMednum(om_id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		} else {
			try {
				odm.deleteMedOrder(om_id);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		Order curOrder = odm.getCurrentOrderByUserId(user_id);
		int order_id = curOrder.getId();
		// ���¶����ܶ�
		Float sum = odm.getOrderTotalSum(order_id);
		System.out.print("�����ܺ�sum:" + sum);
		Map<String,Object> map = new HashMap<>();
		try {
			map = new HashMap<>();
			map.put("total_sum", sum);
			map.put("order_id", order_id);
			odm.updateSumToOrder(map);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���¶����ܶ�ʧ��");
			return -1;
		}
		return 1;
	}

	
	// 0Ϊ����ѿ۳����ύ����ʧ�ܣ�1Ϊ�ɹ���-1Ϊ����,-2Ϊ�û����۳�ʧ���Ҷ���δ�ύ,-3Ϊ����֧���ɹ������ݿ�����ܶ�ʧ��
	@Override
	public int payNormalOrder(int user_id, int speed,String name,String phone,String address) {
		Float money = odm.getUserMoney(user_id);
		Order curOrder = odm.getCurrentOrderByUserId(user_id);
		int order_id = curOrder.getId();
		Float medsum = odm.getOrderTotalSum(order_id);
		float price ;  //���ͼ۸�
		if (speed ==0) {
			price = 4;
		}else {
			price = 2;
		}
		if (money < (price+medsum)) {
			return -1;
		}
		Map<String,Object> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("spend", price+medsum);
		try {
			odm.subUserMoney(map);
		}catch (Exception e) {
			e.printStackTrace();
			return -2;
		}
		//���¶�����������Ϣ
		map = new HashMap<>();
		map.put("speed", speed);
		map.put("user_name",name);
		map.put("user_phone", phone);
		map.put("user_address", address);
		map.put("order_id", order_id);
		odm.updateOrderUserInfo(map);
		
		//���¶�����״̬
		map = new HashMap<>();
		map.put("order_id", order_id);
		map.put("state", 1);
		try {
			odm.updateOrderState(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		map = new HashMap<>();
		map.put("order_id", order_id);
		map.put("price", price);
		try {
			odm.addSumToOrder(map);
		}catch (Exception e) {
			e.printStackTrace();
			return -3;
		}
		return 1;
		
	}

	@Override
	public int postPrescriptOrder(int user_id, int speed, String name, String phone, String address, String picPath) {
		Float money = odm.getUserMoney(user_id);
		Order curOrder = odm.getCurrentOrderByUserId(user_id);
		int order_id = curOrder.getId();
		Float medsum = odm.getOrderTotalSum(order_id);
		float price ;  //���ͼ۸�
		if (speed ==0) {
			price = 4;
		}else {
			price = 2;
		}
		if (money < (price+medsum)) {
			return -1;
		}
		Map<String,Object> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("spend", price+medsum);
		try {
			odm.subUserMoney(map);
		}catch (Exception e) {
			e.printStackTrace();
			return -2;
		}
		//���¶�����������Ϣ
		map = new HashMap<>();
		map.put("speed", speed);
		map.put("user_name",name);
		map.put("user_phone", phone);
		map.put("user_address", address);
		map.put("order_id", order_id);
		map.put("check_pic", picPath);
		odm.updateCheckOrderInfo(map);
		
		//���¶�����״̬
		map = new HashMap<>();
		map.put("order_id", order_id);
		map.put("state", 1);
		try {
			odm.updateOrderState(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		map = new HashMap<>();
		map.put("order_id", order_id);
		map.put("price", price);
		try {
			odm.addSumToOrder(map);
		}catch (Exception e) {
			e.printStackTrace();
			return -3;
		}
		return 1;
	}

	@Override
	public List<Order> getWaitingOrderByTimeDesc() {
		List<Order> orderList = odm.getWaitingOrderListByDescTime();
		if (orderList.size()>0) {
			for(Order order:orderList) {
				order.setMedList(odm.getMedListByOrderId(order.getId()));
			}
		}
		return orderList;
	}

	@Override
	public OrderDetail getOrderDetailById(int order_id) {
		// TODO Auto-generated method stub
		OrderDetail oDetail  = odm.getOrderInfoByOrderId(order_id);
		oDetail.setMedList(odm.getMedListByOrderId(order_id));
		return oDetail;
	}

	
	@Override
	public int takeOrderByDistId(int dist_id, int order_id) {
		Map<String,Object> map = new HashMap<>();
		map.put("dist_id", dist_id);
		map.put("order_id", order_id);
		try {
			odm.takeOrderByDistId(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int finishOrder(int order_id) {
		try {
			odm.finishOrder(order_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List<Order> getTakedOrder(int dist_id) {
		List<Order> orderList = odm.getTakedOrderListByDistId(dist_id);
		if (orderList.size()>0) {
			for(Order order:orderList) {
				order.setMedList(odm.getMedListByOrderId(order.getId()));
			}
		}
		return orderList;
	}

	@Override
	public List<Order> getFinishedOrder(int dist_id) {
		List<Order> orderList = odm.getFinishedOrderListByDistId(dist_id);
		// TODO Auto-generated method stub
		if (orderList.size()>0) {
			for(Order order:orderList) {
				order.setMedList(odm.getMedListByOrderId(order.getId()));
			}
		}
		return orderList;
		
	}

	@Override
	public List<Order> getStartingOrderByUserId(int user_id) {
		List<Order> list = odm.getStartingOrderByUserId(user_id);
		if (list.size()>0) {
			for(Order order:list) {
				int order_id = order.getId();
				List<MedInOrder> medlist = odm.getMedListByOrderId(order_id);
				if (medlist.size()>0) {
					order.setMedList(medlist);
				}
			}
			return list;
		}else {
			return list;
		}		
	}

	@Override
	public List<Order> getFinishedOrderByUserId(int user_id) {
		List<Order> list = odm.getFinishedOrderByUserId(user_id);
		if (list.size()>0) {
			for(Order order:list) {
				int order_id = order.getId();
				List<MedInOrder> medlist = odm.getMedListByOrderId(order_id);
				if (medlist.size()>0) {
					order.setMedList(medlist);
				}
			}
			return list;
		}else {
			return list;
		}		
	}

}
