package com.zealouscoder.grow;

import java.util.HashMap;
import java.util.Map;

import com.zealouscoder.grow.cells.CellContainer;
import com.zealouscoder.grow.cells.CellType;
import com.zealouscoder.grow.cells.GrowCell;

public class GrowGrid extends GameObject {

	private Map<Integer, GrowCell> cells = new HashMap<Integer, GrowCell>();
	private int maxX = Integer.MIN_VALUE;
	private int minX = Integer.MAX_VALUE;
	private int maxY = Integer.MIN_VALUE;
	private int minY = Integer.MAX_VALUE;
	private boolean hasLife;
	private Object cellsLock = new Object();

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

	public void addTo(GrowCell cell) {
		if (hasCell(cell)) {
			CellContainer container = null;
			if (getAt(cell).isContainer()) {
				container = (CellContainer) getAt(cell);
			} else {
				container = new CellContainer(cell.getX(), cell.getY());
				container.add(getAt(cell));
			}
			container.add(cell);
			set(container);
		}
	}

	private GrowCell getAt(GrowCell cell) {
		return getAt(cell.getX(), cell.getY());
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
		if (cell.isAlive()) {
			hasLife = true;
		}
		synchronized (cellsLock) {
			cells.put(calcIndex(x, y), cell);
			cellsLock.notifyAll();
		}		
	}

	public boolean hasLife() {
		return hasLife;
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
		GrowCell[] allCells = null;
		synchronized (cellsLock) {
			allCells = cells.values().toArray(new GrowCell[cells.size()]);
			cellsLock.notifyAll();
		}
		return allCells;
	}

	public boolean hasCell(GrowCell cell) {
		GrowCell cellAt = getAt(cell.getX(), cell.getY());
		return cellAt != null && cellAt != cell;
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

	public boolean is(CellType type, int x, int y) {
		GrowCell cell = getAt(x, y);
		if (cell != null) {
			return getAt(x, y).getType() == type;
		}
		return false;
	}
}
