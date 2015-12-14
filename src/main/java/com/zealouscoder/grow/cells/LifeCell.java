package com.zealouscoder.grow.cells;

import com.zealouscoder.grow.RenderVisitor;
import com.zealouscoder.grow.UpdateVisitor;

public class LifeCell extends GrowCell {

	public LifeCell(int x, int y) {
		super(CellType.LIFE, x, y);
	}

	@Override
	public boolean isPassable() {
		return true;
	}

	@Override
	public void render(RenderVisitor visitor) {
		visitor.render(this);
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		visitor.update(dt, this);
	}
	
	public boolean isAlive() {
		return true;
	}

}
