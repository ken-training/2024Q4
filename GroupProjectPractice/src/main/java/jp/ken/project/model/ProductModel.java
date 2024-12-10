package jp.ken.project.model;

import java.io.Serializable;

public class ProductModel implements Serializable {
	private int product_id;										//商品ID
	private String product_name;							//商品名
	private String genre_id;									//ジャンルID
	private int price;												//値段
	private String image;											//画像
	private java.sql.Date sale_start_date;		//発売予定日****データ型不安
	private String product_detail;						//商品説明
	private int stock_qty;										//在庫数
	private String sale_is_valid;								//販売可能フラグ
	private String discnt_is_valid;							//割引フラグ
	private float discnt_rate;								//割引率

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
	public String getGenre_id() {
		return genre_id;
	}
	public void setGenre_id(String genre_id) {
		this.genre_id = genre_id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public java.sql.Date getSale_start_date() {
		return sale_start_date;
	}
	public void setSale_start_date(java.sql.Date sale_start_date) {
		this.sale_start_date = sale_start_date;
	}
	public String getProduct_detail() {
		return product_detail;
	}
	public void setProduct_detail(String product_detail) {
		this.product_detail = product_detail;
	}
	public int getStock_qty() {
		return stock_qty;
	}
	public void setStock_qty(int stock_qty) {
		this.stock_qty = stock_qty;
	}
	public String getSale_is_valid() {
		return sale_is_valid;
	}
	public void setSale_is_valid(String sale_is_valid) {
		this.sale_is_valid = sale_is_valid;
	}
	public String getDiscnt_is_valid() {
		return discnt_is_valid;
	}
	public void setDiscnt_is_valid(String discnt_is_valid) {
		this.discnt_is_valid = discnt_is_valid;
	}
	public float getDiscnt_rate() {
		return discnt_rate;
	}
	public void setDiscnt_rate(float discnt_rate) {
		this.discnt_rate = discnt_rate;
	}



}
