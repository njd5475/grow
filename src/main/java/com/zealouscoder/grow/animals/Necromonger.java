package com.zealouscoder.grow.animals;

import com.zealouscoder.grow.Animal;
import com.zealouscoder.grow.RenderVisitor;
import com.zealouscoder.grow.UpdateVisitor;
import com.zealouscoder.grow.cells.GrowCell;

public class Necromonger extends Animal {

	public Necromonger(double x, double y) {
		super(x, y);
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
