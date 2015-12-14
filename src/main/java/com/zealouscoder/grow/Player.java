package com.zealouscoder.grow;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class Player extends GameObject {

	private AffineTransform rotation = new AffineTransform();
	private List<Goal> goals = new LinkedList<Goal>();
	
	private int x = 0;
	private int y = 0;

	// starting direction is north
	private int dx = 0;
	private int dy = -1;

	// one block per second
	private int speed = 1;
	private int lastY;
	private int lastX;
	private Goal currentGoal;
	
	private Item inventory = Item.LIFE;

	public Player() {
		addNewGoal("Create Life"); 
		rotation.rotate(Math.toRadians(90));
	}

	public int getSpeed() {
		return speed;
	}

	public void addNewGoal(String goal) {
		currentGoal = new Goal(goal);
		goals.add(currentGoal);
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

	public void revertToLast() {
		x = lastX;
		y = lastY;
	}

	public void moveToOtherSideOfGrid(GrowGrid grid) {
		x -= dx * grid.getWidth();
		y -= dy * grid.getHeight();
	}

	public void rotate() {
		Point2D dst = new Point2D.Double(0, 0);
		rotation.transform(new Point2D.Double(dx, dy), dst);
		dx = (int) dst.getX();
		dy = (int) dst.getY();
	}

	public Goal getGoal() {
		return currentGoal;
	}

	public void dropItem(GrowGame game) {
		useItem(game);
	}

	public Item getCurrentItem() {
		return inventory;
	}

	public void useItem(GrowGame growGame) {
		growGame.swapFor(getCurrentItem().getType(), getX(), getY());
		inventory = Item.NONE;
	}
}
