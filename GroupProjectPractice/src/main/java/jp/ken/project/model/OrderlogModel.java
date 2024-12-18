package jp.ken.project.model;

import java.io.Serializable;
import java.sql.Date;

public class OrderlogModel implements Serializable {
	private int order_detail_id;
	private int order_id;
	private Date order_date;
	private int product_id;
	private String product_name;
	private int product_num;
	private int temp_amount;
	private Integer use_points;
	private int customer_id;
	private String customer_name;
	private int ship_id;
	private String ship_name;
	private String ship_address;
	private String payment_methods;
	private Date ship_date;
	private String status;

	public int getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(int order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getProduct_num() {
		return product_num;
	}
	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}
	public int getTemp_amount() {
		return temp_amount;
	}
	public void setTemp_amount(int temp_amount) {
		this.temp_amount = temp_amount;
	}
	public Integer getUse_points() {
		return use_points;
	}
	public void setUse_points(Integer use_points) {
		this.use_points = use_points;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public int getShip_id() {
		return ship_id;
	}
	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getShip_address() {
		return ship_address;
	}
	public void setShip_address(String ship_address) {
		this.ship_address = ship_address;
	}
	public String getPayment_methods() {
		return payment_methods;
	}
	public void setPayment_methods(String payment_methods) {
		this.payment_methods = payment_methods;
	}
	public Date getShip_date() {
		return ship_date;
	}
	public void setShip_date(Date ship_date) {
		this.ship_date = ship_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
