package com.zealouscoder.grow;

import com.zealouscoder.grow.cells.CellType;
import com.zealouscoder.grow.cells.GrowCell;
import com.zealouscoder.grow.cells.LifeCell;

public enum Item {
	LIFE(CellType.LIFE);
	
	private CellType type;

	Item(CellType type) {
		this.type = type;
	}
}
