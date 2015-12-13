package com.zealouscoder.grow;

public abstract class GrowCell extends GameObject {

	private int x = 0;
	private int y = 0;
	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	
	public GrowCell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
}
