package com.zealouscoder.grow;

import com.zealouscoder.grow.cells.SpawnerCell;

public interface GameObjectFactory {

	public GameObject newObject(SpawnerCell spawnerCell, GrowGame game);
}
