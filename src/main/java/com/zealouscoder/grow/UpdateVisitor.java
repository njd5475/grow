package com.zealouscoder.grow;

import com.zealouscoder.grow.animals.Necromonger;
import com.zealouscoder.grow.cells.EmptyCell;
import com.zealouscoder.grow.cells.GrowingCell;
import com.zealouscoder.grow.cells.SpawnerCell;

public interface UpdateVisitor {

	public void update(double dt, GrowGame growGame);

	public void update(double dt, GrowGrid growGrid);

	public void update(double dt, GrowingCell growingCell);

	public void update(double dt, EmptyCell emptyCell);

	public void update(double dt, Player player);

	public void update(double dt, SpawnerCell spawnerCell);

	public void update(double dt, Necromonger necromonger);

}
