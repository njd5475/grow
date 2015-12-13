package com.zealouscoder.grow;

public class DefaultUpdateVisitor implements UpdateVisitor {

	private GrowGame game;
	private double playerMoveAccumulator;
	
	
	public DefaultUpdateVisitor(GrowGame game) {
		this.game = game;
	}

	@Override
	public void update(double dt, GrowGame growGame) {
		growGame.getGrid().update(dt, this);
		growGame.decreaseGrowthRate(dt * 0.001);
		growGame.getCurrentPlayer().update(dt, this);
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
	}

}
