package entity;

import java.security.KeyStore.PrivateKeyEntry;
import java.sql.Time;
import java.sql.Timestamp;

public class Order {

	private int id;
	private float total_sum;
	private Timestamp create_time;
	private int user_id;
	private int distributer_id;
	private String user_address;
	private String store_address;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getTotal_sum() {
		return total_sum;
	}
	public void setTotal_sum(float total_sum) {
		this.total_sum = total_sum;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getDistributer_id() {
		return distributer_id;
	}
	public void setDistributer_id(int distributer_id) {
		this.distributer_id = distributer_id;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getStore_address() {
		return store_address;
	}
	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}
	
	

}
