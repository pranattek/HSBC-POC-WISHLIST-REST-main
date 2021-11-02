package com.albertsonspoc.dto;

public class WishListCountChangeDto {
	int id;
	boolean increment;

	public WishListCountChangeDto() {
	}

	public WishListCountChangeDto(int id, boolean increment) {
		this.id = id;
		this.increment = increment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isIncrement() {
		return increment;
	}

	public void setIncrement(boolean increment) {
		this.increment = increment;
	}
}
