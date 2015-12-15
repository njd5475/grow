package com.zealouscoder.grow.cells;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.zealouscoder.grow.RenderVisitor;
import com.zealouscoder.grow.UpdateVisitor;

public class LifeCell extends GrowCell {

	private static final Random rnd = new Random(System.currentTimeMillis());

	private boolean spawningActive = true;
	private double spawnTime;
	private Set<CellType> lifeTypes = new HashSet<CellType>();
	private static final int DEFAULT_TIMER = 5;
	
	public LifeCell(int x, int y) {
		super(CellType.LIFE, x, y);
		resetTimer();
		add(CellType.LIFE);
	}

	public void add(CellType life) {
		if (life == null) {
			throw new NullPointerException("Life types cannot be null");
		}
		lifeTypes.add(life);
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
	
	public void activate() {
		spawningActive = true;
		resetTimer();
	}
	
	private void resetTimer() {
		spawnTime = DEFAULT_TIMER;
	}

	public void deactivate() {
		spawningActive = false;
	}

	public boolean isSpawnTime(double dt) {
		if (spawningActive) {
			spawnTime -= dt;

			if(spawnTime <= 0) {
				resetTimer();
				return true;
			}
		}

		return false;
	}

	public Point getNextSpawnPoint() {
		int chX = rnd.nextInt(3) - 1;
		int chY = rnd.nextInt(3) - 1;
		return new Point(getX() + chX, getY() + chY);
	}

	public CellType getNextSpawnType() {
		return lifeTypes.toArray(new CellType[lifeTypes.size()])[rnd.nextInt(lifeTypes.size())];
	}

}
