package jp.ken.project.model;

import java.io.Serializable;

public class ShippingModel implements Serializable{
	private int ship_id;
	private String ship_name;
	private String ship_phonetic;
	private String ship_zip;
	private String ship_address;
	private String ship_phone;

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
	public String getShip_phonetic() {
		return ship_phonetic;
	}
	public void setShip_phonetic(String ship_phonetic) {
		this.ship_phonetic = ship_phonetic;
	}
	public String getShip_zip() {
		return ship_zip;
	}
	public void setShip_zip(String ship_zip) {
		this.ship_zip = ship_zip;
	}
	public String getShip_address() {
		return ship_address;
	}
	public void setShip_address(String ship_address) {
		this.ship_address = ship_address;
	}
	public String getShip_phone() {
		return ship_phone;
	}
	public void setShip_phone(String ship_phone) {
		this.ship_phone = ship_phone;
	}

}
