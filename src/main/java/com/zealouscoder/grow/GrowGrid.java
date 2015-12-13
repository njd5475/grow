package com.zealouscoder.grow;

import java.util.HashMap;
import java.util.Map;

import com.zealouscoder.grow.cells.GrowCell;

public class GrowGrid extends GameObject {

	private Map<Integer, GrowCell> cells = new HashMap<Integer, GrowCell>();
	private int maxX = Integer.MIN_VALUE;
	private int minX = Integer.MAX_VALUE;
	private int maxY = Integer.MIN_VALUE;
	private int minY = Integer.MAX_VALUE;

	public GrowGrid() {

	}

	/**
	 * This will overflow if we go outside of the range sqrt(Integer.MAX_VALUE).
	 * <br/>
	 * But not to worry we won't, hehehe!!!!
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private static int calcIndex(int x, int y) {
		return String.format("%dx%d", x, y).hashCode();
	}

	public void set(GrowCell cell) {
		if (cell == null) {
			throw new NullPointerException("Cannot set a cell to nothing!");
		}
		int x = cell.getX();
		int y = cell.getY();
		maxX = Math.max(maxX, x);
		minX = Math.min(minX, x);
		maxY = Math.max(maxY, y);
		minY = Math.min(minY, y);
		cells.put(calcIndex(x, y), cell);
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		visitor.update(dt, this);
	}

	@Override
	public void render(RenderVisitor visitor) {
		visitor.render(this);
	}

	public synchronized GrowCell[] getCells() {
		return cells.values().toArray(new GrowCell[cells.size()]);
	}

	public GrowCell getAt(int x, int y) {
		return cells.get(calcIndex(x, y));
	}

	public int getWidth() {
		return maxX - minX + 1;
	}
	
	public int getHeight() {
		return maxY - minY + 1;
	}
}
