package com.zealouscoder.grow;

public abstract class Animal extends GameObject {

	private double x;
	private double y;

	public Animal(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

}
