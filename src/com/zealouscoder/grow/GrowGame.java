package com.zealouscoder.grow;

public class GrowGame extends GameObject {

	private boolean running = true;
	private double gameclock;
	private static final double fixedTimeStep = 0.12d;
	private GrowGrid grid;

	public GrowGame() {
		this.grid = new GrowGrid();
		createGrowingCellAt(0, 0);
	}

	public void createGrowingCellAt(int x, int y) {
		this.grid.set(new GrowingCell(x, y, 1, 10));
	}

	public GrowGrid getGrid() {
		return grid;
	}

	public double getGameClock() {
		return gameclock;
	}

	@Override
	public void render(RenderVisitor visitor) {
		visitor.render(this);
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		gameclock += dt; // advance clock

		visitor.update(dt, this);
	}

	public boolean isRunning() {
		return running = true;
	}

}
