package com.zealouscoder.grow;

import com.zealouscoder.grow.cells.GrowCell;

public abstract class Animal extends GameObject {

	private double x;
	private double y;

	public Animal(GrowCell cell) {
		this.x = cell.getX() * GrowCell.WIDTH;
		this.y = cell.getY() * GrowCell.HEIGHT;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

}
