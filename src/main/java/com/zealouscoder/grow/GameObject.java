package com.zealouscoder.grow;

public abstract class GameObject {

	public GameObject() {
		// TODO Auto-generated constructor stub
	}

	public abstract void render(RenderVisitor visitor);
	
	public abstract void update(double dt, UpdateVisitor visitor);
	
}
