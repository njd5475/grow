package com.zealouscoder.grow;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.zealouscoder.grow.animals.Necromonger;
import com.zealouscoder.grow.cells.CellType;
import com.zealouscoder.grow.cells.EmptyCell;
import com.zealouscoder.grow.cells.GrowCell;
import com.zealouscoder.grow.cells.GrowingCell;
import com.zealouscoder.grow.cells.SpawnerCell;

public class GrowGame extends GameObject {

	private boolean running = true;
	private double gameclock;
	private static final double fixedTimeStep = 0.12d;
	private static double growthRate = 3d;
	private GrowGrid grid;
	private Player currentPlayer;
	private Queue<ButtonEvent> buttonQueue = new ConcurrentLinkedQueue<ButtonEvent>();
	private Queue<GameObject> spawnQueue = new ConcurrentLinkedQueue<GameObject>();
	private Map<Integer, Integer> buttonStatus = new HashMap<Integer, Integer>();
	private Set<GameObject> objects = new HashSet<GameObject>();
	private boolean deathEmerged = false;
	private Random rnd = new Random(System.currentTimeMillis());

	public GrowGame() {
		this.currentPlayer = new Player();
		this.grid = new GrowGrid();
		createGrowingCellAt(0, 0);
	}

	public void drainSpawnQueue() {
		GameObject object = null;
		while ((object = spawnQueue.poll()) != null) {
			objects.add(object);
		}
	}

	public Set<GameObject> getObjects() {
		return objects;
	}

	public void createGrowingCellAt(int x, int y) {
		if (this.grid.getAt(x, y) == null) {
			this.grid.set(new GrowingCell(x, y, 1, growthRate));
		}
	}

	public void decreaseGrowthRate(double amount) {
		// decrease growth rate each time it grows by a small fraction
		growthRate += growthRate * amount;
	}

	public GrowGrid getGrid() {
		return grid;
	}

	public double getGameClock() {
		return gameclock;
	}

	public double getGrowthRate() {
		return growthRate;
	}

	public Dimension getGridDimensions() {
		return new Dimension(grid.getWidth(), grid.getHeight());
	}

	@Override
	public void render(RenderVisitor visitor) {
		visitor.render(this);
	}

	@Override
	public void update(double dt, UpdateVisitor visitor) {
		gameclock += dt; // advance clock

		visitor.update(dt, this);
	}

	public boolean isRunning() {
		return running = true;
	}

	public void createEmptyCellAt(int x, int y) {
		GrowCell at = grid.getAt(x, y);
		if (at != null) {
			throw new UnsupportedOperationException("Cannot create a new cell where an old cell stands!");
		}
		grid.set(new EmptyCell(x, y));
	}

	public void swapWithEmptyCell(GrowCell toSwap) {
		int x = toSwap.getX();
		int y = toSwap.getY();
		GrowCell at = grid.getAt(x, y);
		swap(toSwap, new EmptyCell(x, y));
	}

	private void swap(GrowCell toSwap, GrowCell with) {
		if (toSwap == null || with == null) {
			throw new NullPointerException("Cannot attempt to swap a null cell or replace a cell with a null cell!");
		}
		if (toSwap == with) {
			throw new UnsupportedOperationException("Swapping a cell with itself is redundant");
		}
		grid.set(with);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void queueButtonEvent(int keyPressed, int keyCode) {
		buttonQueue.add(new ButtonEvent(keyPressed, keyCode));
	}

	public boolean hasCell(int x, int y) {
		return grid.getAt(x, y) != null;
	}

	public void updateButtonStates() {
		ButtonEvent event = null;

		buttonStatus.clear();

		// drain queue
		while ((event = buttonQueue.poll()) != null) {
			buttonStatus.put(event.getKeyCode(), event.getKeyStatus());
		}
	}

	public boolean isKeyDown(int keyCode) {
		return buttonStatus.get(keyCode) != null && buttonStatus.get(keyCode) == KeyEvent.KEY_PRESSED;
	}

	public boolean isKeyUp(int keyCode) {
		return buttonStatus.get(keyCode) != null && buttonStatus.get(keyCode) == KeyEvent.KEY_RELEASED;
	}

	public GrowCell swapFor(CellType type, GrowCell cell) {
		GrowCell newCell = createCell(type, cell.getX(), cell.getY());
		swap(newCell, cell);
		return newCell;
	}

	public GrowCell swapFor(CellType type, int x, int y) {
		GrowCell newCell = createCell(type, x, y);
		swap(grid.getAt(x, y), newCell);
		return newCell;
	}

	public void add(GameObject go) {
		spawnQueue.add(go);
	}

	public void placeItem(Player player) {
		player.useItem(this);
	}

	public boolean hasLife() {
		return grid.hasLife();
	}

	public boolean deathHasEmerged() {
		return deathEmerged;
	}

	public void makeDeath() {
		int width = grid.getWidth() - 1;
		int height = grid.getHeight() - 1;
		int total = width * height;
		double percentageOfTotal = total * 0.1d;
		int num = (int) Math.ceil(percentageOfTotal);
		System.out.println("Spawning " + num + " monster spawners");
		for (int i = 0; i < num; ++i) {
			// spawn somewhere
			int nextInt = rnd.nextInt(total);
			int y = nextInt / width;
			int x = nextInt - y * width;
			x -= width / 2;
			y -= height / 2;
			if (grid.is(CellType.EMPTY, x, y)) {
				SpawnerCell spawner = new SpawnerCell(x, y, new GameObjectFactory() {
					@Override
					public GameObject newObject(SpawnerCell spawner, GrowGame game) {
						return new Necromonger(spawner.getX() * GrowCell.WIDTH, spawner.getY() * GrowCell.HEIGHT);
					}
				});
				spawner.setRepeating();
				spawner.reset(rnd.nextInt(10) + 10);
				spawner.activate();
				grid.addTo(spawner);
				System.out.println("Created spawner");
			}
		}
		deathEmerged = true;
	}

	private static GrowCell createCell(CellType type, int x, int y) {
		Constructor<? extends GrowCell> k;
		try {
			k = type.getCellClass().getConstructor(int.class, int.class);
			return k.newInstance(x, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean is(int x, int y, CellType empty) {
		return grid.is(empty, x, y);
	}

	public boolean is(GrowCell cell, CellType empty) {
		return grid.is(empty, cell.getX(), cell.getY());
	}

	public boolean hasCellEastAndWest(GrowingCell cell) {
		return hasCell(cell.getX() + 1, cell.getY()) && hasCell(cell.getX() - 1, cell.getY());
	}

	public boolean hasCellNorthAndSouth(GrowingCell cell) {
		return hasCell(cell.getX(), cell.getY() + 1) && hasCell(cell.getX(), cell.getY() - 1);
	}

	public boolean hasCellNorth(GrowingCell cell) {
		return hasCell(cell.getX(), cell.getY() - 1);
	}

	public boolean hasCellWest(GrowingCell cell) {
		return hasCell(cell.getX() - 1, cell.getY());
	}

	public boolean hasCellEast(GrowingCell cell) {
		return hasCell(cell.getX() + 1, cell.getY());
	}

	public boolean hasCellSouth(GrowingCell cell) {
		return hasCell(cell.getX(), cell.getY() + 1);
	}
}
