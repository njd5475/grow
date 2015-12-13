package com.zealouscoder.grow;

public class EmptyCell extends GrowCell {

	public EmptyCell(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(RenderVisitor visitor) {
		visitor.render(this);
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		visitor.update(dt, this);
	}

}
