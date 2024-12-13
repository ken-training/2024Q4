package jp.ken.project.model;

import java.io.Serializable;

public class OrderModel implements Serializable{
	private int order_id;
	private java.sql.Date order_date;
	private int customer_id;
	private int ship_id;
	private int total_amount;
	private String payment_methods;
	private int use_points;
	private String specify_delivery;

	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public java.sql.Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(java.sql.Date order_date) {
		this.order_date = order_date;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getShip_id() {
		return ship_id;
	}
	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}
	public int getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}
	public String getPayment_methods() {
		return payment_methods;
	}
	public void setPayment_methods(String payment_methods) {
		this.payment_methods = payment_methods;
	}
	public int getUse_points() {
		return use_points;
	}
	public void setUse_points(int use_points) {
		this.use_points = use_points;
	}
	public String getSpecify_delivery() {
		return specify_delivery;
	}
	public void setSpecify_delivery(String specify_delivery) {
		this.specify_delivery = specify_delivery;
	}

}
