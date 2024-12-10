package jp.ken.project.model;

public class ProductModel {
	private int product_id;
	private String product_name;
	private int genre_id;
	private int price;
	private String image;
	private int sale_start_date;
	private String product_detail;
	private int stock_qty;
	private int sale_id_valid;
	private int discnt_is_valid;
	private float discnt_rate;

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
	public int getGenre_id() {
		return genre_id;
	}
	public void setGenre_id(int genre_id) {
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
	public int getSale_start_date() {
		return sale_start_date;
	}
	public void setSale_start_date(int sale_start_date) {
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
	public int getSale_id_valid() {
		return sale_id_valid;
	}
	public void setSale_id_valid(int sale_id_valid) {
		this.sale_id_valid = sale_id_valid;
	}
	public int getDiscnt_is_valid() {
		return discnt_is_valid;
	}
	public void setDiscnt_is_valid(int discnt_is_valid) {
		this.discnt_is_valid = discnt_is_valid;
	}
	public float getDiscnt_rate() {
		return discnt_rate;
	}
	public void setDiscnt_rate(float discnt_rate) {
		this.discnt_rate = discnt_rate;
	}
}
