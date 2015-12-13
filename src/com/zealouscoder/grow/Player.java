package com.zealouscoder.grow;

public class Player extends GameObject {

	private int x = 0;
	private int y = 0;
	
	//starting direction is north
	private int dx = 0;
	private int dy = -1; 
	
	// one block per second
	private int speed = 1;
	private int lastY;
	private int lastX; 
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	public int getSpeed() {
		return speed;
	}

	@Override
	public void render(RenderVisitor visitor) {
		visitor.render(this);
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		visitor.update(dt, this);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getDirectionX() {
		return dx;
	}
	
	public int getDirectionY() {
		return dy;
	}
	
	public int getLastX() {
		return lastX;
	}
	
	public int getLastY() {
		return lastY;
	}
	
	public void moveInDirection() {
		lastX = x;
		lastY = y;
		x += dx;
		y += dy;
	}
}
