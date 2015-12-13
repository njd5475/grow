package com.zealouscoder.grow.cells;

public enum CellType {
	EMPTY(EmptyCell.class),
	GROWING(GrowingCell.class),
	LIFE(LifeCell.class);
	
	private Class<? extends GrowCell> cellClass;

	CellType(Class<? extends GrowCell> cellClass) {
		this.cellClass = cellClass;
	}
	
	public Class<? extends GrowCell> getCellClass() {
		return cellClass;
	}
}
