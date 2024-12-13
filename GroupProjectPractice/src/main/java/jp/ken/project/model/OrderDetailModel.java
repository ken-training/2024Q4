package jp.ken.project.model;

import java.io.Serializable;

public class OrderDetailModel implements Serializable {
	private int order_detail_id;
	private int order_id;
	private int product_id;
	private int product_num;
	private int temp_amount;

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
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
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

}
