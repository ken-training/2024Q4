package jp.ken.project.model;

import java.io.Serializable;

public class CartModel implements Serializable{
	private int product_id;
	private String product_name;
	private int price;
	private int count;
	private String image;
	private String discnt_is_valid;							//割引フラグ
	private Float discnt_rate;					//割引率

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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDiscnt_is_valid() {
		return discnt_is_valid;
	}
	public void setDiscnt_is_valid(String discnt_is_valid) {
		this.discnt_is_valid = discnt_is_valid;
	}
	public Float getDiscnt_rate() {
		return discnt_rate;
	}
	public void setDiscnt_rate(Float discnt_rate) {
		this.discnt_rate = discnt_rate;
	}


}
