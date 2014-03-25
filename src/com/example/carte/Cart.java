package com.example.carte;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Cart {
	private ArrayList<ItemDetails> itemList;
	private BigDecimal budget;
	private BigDecimal totalCost;

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public ArrayList<ItemDetails> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<ItemDetails> itemList) {
		this.itemList = itemList;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public void addItemDetails(ItemDetails i) {
		itemList.add(i);
	}

	public boolean removeItemDetails(ItemDetails i) {
		itemList.remove(i);
		return true;
	}

	public Cart() {
		budget = new BigDecimal("100");// Default value of the budget
		budget.setScale(2, BigDecimal.ROUND_CEILING);
		itemList = new ArrayList<ItemDetails>();
	}

	public Cart(BigDecimal budget, BigDecimal totalCost) {
		super();
		this.budget = budget;
		budget = budget.setScale(2, BigDecimal.ROUND_CEILING);
		this.totalCost = totalCost;
		totalCost = totalCost.setScale(2, BigDecimal.ROUND_CEILING);
		itemList = new ArrayList<ItemDetails>();
	}

	public void addToTotalCost(BigDecimal b) {
		totalCost = this.totalCost.add(b);
		totalCost = totalCost.setScale(2, BigDecimal.ROUND_CEILING);
	}

	public void subtractFromTotalCost(BigDecimal b) {
		totalCost = this.totalCost.subtract(b);
		totalCost = totalCost.setScale(2, BigDecimal.ROUND_CEILING);
	}
	
	public void addToBudget(BigDecimal b) {
		budget = this.budget.add(b);
		budget = budget.setScale(2, BigDecimal.ROUND_CEILING);
	}

	public void subtractFromBudget(BigDecimal b) {
		budget = this.budget.subtract(b);
		budget = budget.setScale(2, BigDecimal.ROUND_CEILING);
	}
}
