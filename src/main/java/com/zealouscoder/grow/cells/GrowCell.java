package com.zealouscoder.grow.cells;

import java.util.Comparator;

import com.zealouscoder.grow.GameObject;

public abstract class GrowCell extends GameObject {
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	public static final Comparator<? super GrowCell> LAYER_SORTED = new Comparator<GrowCell>() {

		@Override
		public int compare(GrowCell o1, GrowCell o2) {
			return 0;
		}
	};

	private int x = 0;
	private int y = 0;
	private CellType type;

	public GrowCell(CellType type, int x, int y) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public int getLayer() {
		return type.getLayer();
	}

	public CellType getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public abstract boolean isPassable();

	public boolean isContainer() {
		return false;
	}

}
