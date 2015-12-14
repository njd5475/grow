package com.zealouscoder.grow;

import com.zealouscoder.grow.cells.CellType;

public enum Item {
	LIFE(CellType.LIFE),
	NONE(CellType.EMPTY);

	private CellType type;

	Item(CellType type) {
		this.type = type;
	}

	public CellType getType() {
		return type;
	}
}
