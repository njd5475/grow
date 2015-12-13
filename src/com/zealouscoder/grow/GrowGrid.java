package com.zealouscoder.grow;

import java.util.HashMap;
import java.util.Map;

public class GrowGrid extends GameObject {

	private static final int HALF_MAX = Integer.MAX_VALUE / 2;
	private static final int CENTER_X = HALF_MAX, CENTER_Y = HALF_MAX;
	private Map<Integer, GrowCell> cells = new HashMap<Integer, GrowCell>();

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
		x += HALF_MAX;
		y += HALF_MAX;
		return y * Integer.MAX_VALUE + x;
	}

	public void set(GrowCell cell) {
		if (cell == null) {
			throw new NullPointerException("Cannot set a cell to nothing!");
		}
		cells.put(calcIndex(cell.getX(), cell.getY()), cell);
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		visitor.update(dt, this);
	}

	@Override
	public void render(RenderVisitor visitor) {
		visitor.render(this);
	}

	public GrowCell[] getCells() {
		return cells.values().toArray(new GrowCell[cells.size()]);
	}

	public GrowCell getAt(int x, int y) {
		return cells.get(calcIndex(x, y));
	}

}
