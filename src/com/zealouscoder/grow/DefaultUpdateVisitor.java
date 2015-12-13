package com.zealouscoder.grow;

public class DefaultUpdateVisitor implements UpdateVisitor {

	private GrowGame game;

	public DefaultUpdateVisitor(GrowGame game) {
		this.game = game;
	}

	@Override
	public void update(double dt, GrowGame growGame) {
		growGame.getGrid().update(dt, this);
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
			System.out.println("Cell has become fully grown");
			game.swapWithEmptyCell(growingCell);
		}
	}

	@Override
	public void update(double dt, EmptyCell emptyCell) {
		// do nothing with empty cells their empty
	}

}
