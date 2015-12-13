package com.zealouscoder.grow;

public interface UpdateVisitor {

	public void update(double dt, GrowGame growGame);

	public void update(double dt, GrowGrid growGrid);

	public void update(double dt, GrowingCell growingCell);

	public void update(double dt, EmptyCell emptyCell);

	public void update(double dt, Player player);

}
