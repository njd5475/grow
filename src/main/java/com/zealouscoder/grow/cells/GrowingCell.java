package com.zealouscoder.grow.cells;

import com.zealouscoder.grow.RenderVisitor;
import com.zealouscoder.grow.UpdateVisitor;

public class GrowingCell extends GrowCell {

	// default in seconds
	private double growRate = 1;
	private double timeToGrow;
	private double TotalTimeToGrow;

	public GrowingCell(int x, int y, double growRate, double timeToGrow) {
		super(x, y);
		this.growRate = growRate;
		this.TotalTimeToGrow = timeToGrow;
		this.timeToGrow = 0;
	}

	public double getGrowRate() {
		return growRate;
	}

	public double getTimeToGrow() {
		return timeToGrow;
	}

	public boolean isGrown() {
		return timeToGrow >= TotalTimeToGrow;
	}

	public int getWidth() {
		return (int) (super.getWidth() * (timeToGrow / TotalTimeToGrow));
	}

	public int getHeight() {
		return (int) (super.getWidth() * (timeToGrow / TotalTimeToGrow));
	}
	
	@Override
	public void render(RenderVisitor visitor) {
		visitor.render(this);
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		this.timeToGrow += dt * growRate;
		visitor.update(dt, this);
	}

}
