package com.zealouscoder.grow.cells;

import java.util.Set;
import java.util.TreeSet;

import com.zealouscoder.grow.RenderVisitor;
import com.zealouscoder.grow.UpdateVisitor;

public class CellContainer extends GrowCell {

	private Set<GrowCell> cells = new TreeSet<GrowCell>(GrowCell.LAYER_SORTED);

	public CellContainer(int x, int y) {
		super(CellType.EMPTY, x, y);
	}

	public void add(GrowCell cell) {
		cells.add(cell);
	}

	public void remove(GrowCell cell) {
		cells.remove(cell);
	}

	public Set<GrowCell> getCells() {
		return cells;
	}

	@Override
	public boolean isPassable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(RenderVisitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		// TODO Auto-generated method stub

	}
}
