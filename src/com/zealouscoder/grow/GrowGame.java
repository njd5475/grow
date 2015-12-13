package com.zealouscoder.grow;

public class GrowGame extends GameObject {

	private boolean running = true;
	private double gameclock;
	private static final double fixedTimeStep = 0.12d;
	private static double growthRate = 3d;
	private GrowGrid grid;
	private Player currentPlayer;
	private boolean button1_down = false;
	private boolean button2_down = false;

	public GrowGame() {
		this.currentPlayer = new Player();
		this.grid = new GrowGrid();
		createGrowingCellAt(0, 0);
	}

	public void button1Down() {
		button1_down = true;
	}

	public void button1Up() {
		button1_down = false;
	}

	public void button2Down() {
		button2_down = true;
	}
	
	public void button2Up() {
		button2_down = false;
	}
	
	public boolean isButton1Down() {
		return button1_down;
	}
	
	public boolean isButton2Down() {
		return button2_down;
	}
	
	public boolean isButton1Up() {
		return !button1_down;
	}
	
	public boolean isButton2Up() {
		return !button2_down;
	}

	public void createGrowingCellAt(int x, int y) {
		if (this.grid.getAt(x, y) == null) {
			this.grid.set(new GrowingCell(x, y, 1, growthRate));
		}
	}

	public void decreaseGrowthRate(double amount) {
		// decrease growth rate each time it grows by a small fraction
		growthRate += growthRate * amount;
	}

	public GrowGrid getGrid() {
		return grid;
	}

	public double getGameClock() {
		return gameclock;
	}

	public double getGrowthRate() {
		return growthRate;
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
		GrowCell at = grid.getAt(x, y);
		if (at != null) {
			throw new UnsupportedOperationException("Cannot create a new cell where an old cell stands!");
		}
		grid.set(new EmptyCell(x, y));
	}

	public void swapWithEmptyCell(GrowCell toSwap) {
		int x = toSwap.getX();
		int y = toSwap.getY();
		GrowCell at = grid.getAt(x, y);
		swap(toSwap, new EmptyCell(x, y));
	}

	private void swap(GrowCell toSwap, GrowCell with) {
		if (toSwap == null || with == null) {
			throw new NullPointerException("Cannot attempt to swap a null cell or replace a cell with a null cell!");
		}
		if (toSwap == with) {
			throw new UnsupportedOperationException("Swapping a cell with itself is redundant");
		}
		grid.set(with);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}
