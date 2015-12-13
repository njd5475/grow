package com.zealouscoder.grow;

public class GrowingCell extends GrowCell {

	// default in seconds
	private double growRate = 1;
	private double timeToGrow;
	private double initialTimeToGrow;

	public GrowingCell(int x, int y, double growRate, double timeToGrow) {
		super(x, y);
		this.growRate = growRate;
		this.initialTimeToGrow = timeToGrow;
		this.timeToGrow = 0;
	}

	public double getGrowRate() {
		return growRate;
	}

	public double getTimeToGrow() {
		return timeToGrow;
	}

	public boolean isGrown() {
		return timeToGrow <= 0;
	}

	public int getWidth() {
		return (int) (super.getWidth() * (timeToGrow / initialTimeToGrow));
	}

	public int getHeight() {
		return (int) (super.getWidth() * (timeToGrow / initialTimeToGrow));
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
