package service;

import java.util.List;

import entity.MedInOrder;
import entity.OrderDetail;
import entity.OrderToDist;

public interface OrderService {
	
	int addMedToCurOrder(int med_id,int user_id);
	
	List<MedInOrder> getMedListFromCurOrder(int user_id);
	
	int subMednumInOrder(int om_id,int user_id);
	
	int payNormalOrder(int user_id,int speed,String name,String phone,String address);
	
	int postPrescriptOrder(int user_id,int speed,String name,String phone,String address,String picPath);

	List<OrderToDist> getWaitingOrderByTimeDesc();  //根据时间排序查询order
	
	List<OrderToDist> getTakedOrder(int dist_id);
	
	List<OrderToDist> getFinishedOrder(int dist_id);
	
	OrderDetail getOrderDetailById(int order_id);
	
	int takeOrderByDistId(int dist_id,int order_id) ;
	
	int finishOrder(int order_id);
}
