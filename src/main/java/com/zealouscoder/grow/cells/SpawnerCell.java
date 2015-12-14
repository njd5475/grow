package com.zealouscoder.grow.cells;

import com.zealouscoder.grow.GameObject;
import com.zealouscoder.grow.GameObjectFactory;
import com.zealouscoder.grow.GrowGame;
import com.zealouscoder.grow.RenderVisitor;
import com.zealouscoder.grow.UpdateVisitor;

public class SpawnerCell extends GrowCell {

	private boolean active = false;
	private double timeToSpawn = Double.MAX_VALUE;
	private boolean repeat = false;
	private int spawned = 0;
	private double rate = Double.MAX_VALUE;
	private GameObjectFactory factory;

	public SpawnerCell(int x, int y, GameObjectFactory factory) {
		super(CellType.SPAWNER, x, y);
		this.factory = factory;
	}

	public int getSpawnedCount() {
		return spawned;
	}

	public boolean repeats() {
		return repeat;
	}

	public void activate() {
		active = true;
	}

	public void deactivate() {
		active = false;
	}

	@Override
	public boolean isPassable() {
		return true;
	}

	@Override
	public void render(RenderVisitor visitor) {
		// does not render
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		visitor.update(dt, this);
	}

	public GameObject spawn(double dt, GrowGame game) {
		if (active) {
			GameObject go = null;
			if(timeToSpawn <= 0 && (repeat || spawned <= 0)) {
				go = factory.newObject(this, game);
				++spawned;
				if(repeat) {
					timeToSpawn = rate;
				}
			}
			return go;
		}
		return null;
	}

	public void reset(int i) {
		this.rate = i;
		this.timeToSpawn = this.rate;
	}

}
