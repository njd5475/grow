package com.zealouscoder.grow;

import java.awt.Dimension;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.zealouscoder.grow.cells.EmptyCell;
import com.zealouscoder.grow.cells.GrowCell;
import com.zealouscoder.grow.cells.GrowingCell;

public class GrowGame extends GameObject {

	private boolean running = true;
	private double gameclock;
	private static final double fixedTimeStep = 0.12d;
	private static double growthRate = 3d;
	private GrowGrid grid;
	private Player currentPlayer;
	private Queue<ButtonEvent> buttonQueue = new ConcurrentLinkedQueue<ButtonEvent>();

	public GrowGame() {
		this.currentPlayer = new Player();
		this.grid = new GrowGrid();
		createGrowingCellAt(0, 0);
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
	
	public Dimension getGridDimensions() {
		return new Dimension(grid.getWidth(), grid.getHeight());
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

	public void queueButtonEvent(int keyPressed, int keyCode) {
		buttonQueue.add(new ButtonEvent(keyPressed, keyCode));
	}

	public boolean hasCell(int x, int y) {
		return grid.getAt(x, y) != null;
	}
}
