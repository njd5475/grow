package com.zealouscoder.grow.cells;

import com.zealouscoder.grow.RenderVisitor;
import com.zealouscoder.grow.UpdateVisitor;

public class EmptyCell extends GrowCell {

	public EmptyCell(int x, int y) {
		super(CellType.EMPTY,x, y);
	}

	@Override
	public void render(RenderVisitor visitor) {
		visitor.render(this);
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		visitor.update(dt, this);
	}

	@Override
	public boolean isPassable() {
		return true;
	}

}
