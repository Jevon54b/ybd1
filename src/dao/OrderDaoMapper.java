package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import entity.MedInOrder;
import entity.Order;
import entity.OrderDetail;
import entity.OrderToDist;


public interface OrderDaoMapper {
	
	Order getCurrentOrderByUserId(int user_id);//��ȡ��ǰ����
	
	int createNewCurOrder(int user_id)throws Exception;//������������½���ǰ����
	
	Integer matchOrderMed(Map<String,Object> map); //���� med_id �� order_id ��ѯ�Ƿ����м�¼������������med_num
	
	int addMedToOrder(Map<String, Object> map)throws Exception; //��ҩƷ���뵱ǰ����
	
	int addMednum(int om_id)throws Exception;  //ҩƷnum+1
	
	int subMednum(int om_id)throws Exception;  //ҩƷnum-1
	
	int deleteMedOrder(int om_id)throws Exception; //ɾ��om��¼ ��ʾ�ӵ�ǰ�����Ƴ���ҩƷ
	
	Integer getCurMednumByOMId(int om_id); //��ȡ��ǰҩƷ��������
	
	Float getOrderTotalSum(int order_id); //��ȡ��ǰ�����ܶ�
	
	int updateSumToOrder(Map<String,Object> map)throws Exception;  //���µ�ǰ�����ܶ�
	
	List<MedInOrder> getMedListFromCurOrder(int user_id)throws Exception;
	
	int addSumToOrder(Map<String, Object> map) throws Exception;
	
	int updateOrderState(Map<String, Object> map)throws Exception;
	
	Float getUserMoney(int user_id);
	
	int subUserMoney(Map<String, Object> map)throws Exception;
	
	int updateOrderUserInfo(Map<String, Object> map);
	
	int updateCheckOrderInfo(Map<String, Object> map);
	
	int addCheckToOrder(Map<String, Object> map);
	
	List<Order> getWaitingOrderListByDescTime();
	
	List<Order> getTakedOrderListByDistId(int dist_id);
	
	List<Order> getFinishedOrderListByDistId(int dist_id);
	
	List<MedInOrder> getMedListByOrderId(int order_id);
	
	OrderDetail getOrderInfoByOrderId(int order_id);
	
	int takeOrderByDistId(Map<String, Object> map)throws Exception; //����״̬����Ϊ���ӵ�
	
	int finishOrder(int order_id)throws Exception;//����״̬����Ϊ�������
	
	List<Order> getStartingOrderByUserId(int user_id);
	
	List<Order> getFinishedOrderByUserId(int user_id);
}
