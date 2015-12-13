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

	public void createEmptyCellAt(int x, int y) {
		GrowCell at = grid.getAt(x,y);
		if(at != null) {
			throw new UnsupportedOperationException("Cannot create a new cell where an old cell stands!");
		}
		grid.set(new EmptyCell(x, y));
	}
	
	public void swapWithEmptyCell(GrowCell toSwap) {
		int x = toSwap.getX();
		int y = toSwap.getY();
		GrowCell at = grid.getAt(x,y);
		swap(toSwap, new EmptyCell(x, y));
	}

	private void swap(GrowCell toSwap, GrowCell with) {
		if(toSwap == null || with == null) {
			throw new NullPointerException("Cannot attempt to swap a null cell or replace a cell with a null cell!");
		}
		if(toSwap == with) {
			throw new UnsupportedOperationException("Swapping a cell with itself is redundant");
		}
		grid.set(with);
	}
}
