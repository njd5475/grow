package com.zealouscoder.grow;

import java.awt.event.KeyEvent;

import com.zealouscoder.grow.animals.Necromonger;
import com.zealouscoder.grow.cells.CellContainer;
import com.zealouscoder.grow.cells.EmptyCell;
import com.zealouscoder.grow.cells.GrowCell;
import com.zealouscoder.grow.cells.GrowingCell;
import com.zealouscoder.grow.cells.LifeCell;
import com.zealouscoder.grow.cells.SpawnerCell;

public class DefaultUpdateVisitor implements UpdateVisitor {

	private GrowGame game;
	private double playerMoveAccumulator;
	
	
	public DefaultUpdateVisitor(GrowGame game) {
		this.game = game;
	}

	@Override
	public void update(double dt, GrowGame growGame) {
		//check to exit game
		if(growGame.isKeyUp(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		
		growGame.getGrid().update(dt, this);
		growGame.decreaseGrowthRate(dt * 0.001);
		growGame.getCurrentPlayer().update(dt, this);
		growGame.updateButtonStates();
		for(GameObject obj : growGame.getObjects()) {
			obj.update(dt, this);
		}
		growGame.drainSpawnQueue();
		
		if(!growGame.hasLife() && growGame.getGameClock() > 10d && !growGame.deathHasEmerged()) {
			growGame.makeDeath();
		}
	}

	@Override
	public void update(double dt, GrowGrid growGrid) {
		for(GrowCell cell : growGrid.getCells()) {
			cell.update(dt, this);
		}
	}

	@Override
	public void update(double dt, GrowingCell growingCell) {
		if(growingCell.isGrown()) {
			game.swapWithEmptyCell(growingCell);
			
			int x = growingCell.getX();
			int y = growingCell.getY();
			game.createGrowingCellAt(x-1, y);
			game.createGrowingCellAt(x, y-1);
			game.createGrowingCellAt(x-1, y-1);
			game.createGrowingCellAt(x+1, y);
			game.createGrowingCellAt(x, y+1);
			game.createGrowingCellAt(x+1, y+1);
			game.createGrowingCellAt(x-1, y+1);
			game.createGrowingCellAt(x+1, y-1);
		}
	}

	@Override
	public void update(double dt, EmptyCell emptyCell) {
		// do nothing with empty cells their empty
	}

	@Override
	public void update(double dt, Player player) {
		playerMoveAccumulator += dt;
		
		while(playerMoveAccumulator > player.getSpeed()) {
			player.moveInDirection();
			if(!game.hasCell(player.getX(), player.getY())) {
				player.moveToOtherSideOfGrid(game.getGrid());
			}
			playerMoveAccumulator -= player.getSpeed();
		}
		
		// process movement change for player
		if(game.isKeyDown(KeyEvent.VK_SPACE)) {
			player.rotate();
		}
		
		if(game.isKeyUp(KeyEvent.VK_ENTER)) {
			player.dropItem(game);
		}
	}

	@Override
	public void update(double dt, SpawnerCell spawnerCell) {
		GameObject go = spawnerCell.spawn(dt, game);
		if(go != null) {
			game.add(go);
		}
	}

	@Override
	public void update(double dt, Necromonger necromonger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double dt, CellContainer cellContainer) {
		for(GrowCell cell : cellContainer.getCells()) {
			cell.update(dt, this);
		}
	}

	@Override
	public void update(double dt, LifeCell lifeCell) {
		// TODO Auto-generated method stub
		
	}

}
