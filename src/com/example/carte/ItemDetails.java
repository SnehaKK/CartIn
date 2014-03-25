package com.example.carte;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ItemDetails {

	private String uuid;
	private String storeName;
	private String itemName;
	private int quantity;
	private BigDecimal price;
	private BigDecimal amount;
	private Date date;

	public ItemDetails() {
		uuid=UUID.randomUUID().toString();
		storeName = "";
		itemName = "";
		quantity = 1;
		price = new BigDecimal(0.00);
		date = Calendar.getInstance().getTime();
	}

	public ItemDetails(String uuid, String storeName, String itemName,
			int quantity, BigDecimal price, BigDecimal amount, int budget) {
		super();
		this.uuid = uuid;
		this.storeName = storeName;
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
		this.amount = amount;
	}

	public String getUUID() {

		return uuid;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = new BigDecimal(price);
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount() {
		this.amount = price
				.multiply(new BigDecimal(Integer.toString(quantity)));
		amount = amount.setScale(2, RoundingMode.UP);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ItemDetails getItemDetails(String itemName) {
		ItemDetails item = new ItemDetails();
		return item;
	}

}
