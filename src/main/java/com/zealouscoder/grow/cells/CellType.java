package com.zealouscoder.grow.cells;

public enum CellType {
	EMPTY(EmptyCell.class, 1),
	GROWING(GrowingCell.class, 0),
	LIFE(LifeCell.class, 2),
	SPAWNER(SpawnerCell.class, 1000);
		
	private int layer;
	private Class<? extends GrowCell> cellClass;

	CellType(Class<? extends GrowCell> cellClass, int layer) {
		this.cellClass = cellClass;
		this.layer = layer;
	}
	
	public Class<? extends GrowCell> getCellClass() {
		return cellClass;
	}

	public int getLayer() {
		return layer;
	}
}
