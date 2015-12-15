package com.zealouscoder.grow;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;

import com.zealouscoder.grow.animals.Necromonger;
import com.zealouscoder.grow.cells.CellContainer;
import com.zealouscoder.grow.cells.EmptyCell;
import com.zealouscoder.grow.cells.GrowCell;
import com.zealouscoder.grow.cells.GrowingCell;
import com.zealouscoder.grow.cells.LifeCell;
import com.zealouscoder.grow.cells.SpawnerCell;

public class DefaultRenderVisitor implements RenderVisitor {

	private static final String HELP_STRING = "SPACE - ROTATE DIRECTION, ENTER - PLACE ITEM";
	private Container frame;
	private Font derivedFont;

	private transient Graphics2D g;
	private transient Graphics2D hudOverlay;
	private transient GrowGame game;

	public DefaultRenderVisitor(Container container) {
		this.frame = container;
	}

	@Override
	public void render(GrowGame game) {
		hudOverlay = (Graphics2D) g.create();
		this.game = game;
		game.getGrid().render(this);
		for(GameObject object : game.getObjects()) {
			object.render(this);
		}
		render(game.getCurrentPlayer());
		drawGameClock(game);
		drawControls();
		drawPlayerGoals(game.getCurrentPlayer());
		drawPlayerInventory(game.getCurrentPlayer());
	}

	private void drawPlayerInventory(Player currentPlayer) {
		Graphics2D n = (Graphics2D) hudOverlay.create();
		String item = "Inventory: "
				+ (currentPlayer.getCurrentItem() == null ? "no-item" : currentPlayer.getCurrentItem().name());
		n.drawString(item, 10, (int) ((frame.getHeight() * .2d) + n.getFontMetrics().getHeight() + 2));
		n.dispose();
	}

	private void drawControls() {
		double centeredX = frame.getWidth() / 2 - hudOverlay.getFontMetrics().stringWidth(HELP_STRING) / 2d;
		Graphics2D n = (Graphics2D) hudOverlay.create();
		n.translate(centeredX, frame.getHeight() - n.getFontMetrics().getHeight() - 2);
		n.drawString(HELP_STRING, 0, 0);
		n.dispose();
	}

	private void drawGameClock(GrowGame game) {
		hudOverlay.setColor(Color.blue);
		if (derivedFont == null) {
			derivedFont = g.getFont().deriveFont(Font.BOLD, 24);
		}
		hudOverlay.setFont(derivedFont);
		int currentLine = 0;
		hudOverlay.drawString(String.format("Game Time: %.02f", game.getGameClock()), 10,
				(++currentLine) * (2 + g.getFontMetrics().getHeight()));
		hudOverlay.drawString(String.format("Growth Rate: %.02f", game.getGrowthRate()), 10,
				(++currentLine) * (2 + g.getFontMetrics().getHeight()));
		hudOverlay.drawString(
				String.format("Grid Size: %.00fx%.00f", game.getGridDimensions().getWidth(),
						game.getGridDimensions().getHeight()),
				10, (++currentLine) * (2 + g.getFontMetrics().getHeight()));
	}

	@Override
	public void render(GrowGrid growGrid) {
		for (GrowCell cell : growGrid.getCells()) {
			cell.render(this);
		}
	}

	@Override
	public void render(GrowingCell cell) {
		Graphics2D n = (Graphics2D) g.create();
		center(n);
		n.setColor(Color.white);
		translateToCentered(n, cell);
		n.fillRect(-cell.getWidth() / 2, -cell.getHeight() / 2, cell.getWidth(), cell.getHeight());
		n.dispose();
	}

	@Override
	public void render(EmptyCell emptyCell) {
		Graphics2D n = (Graphics2D) g.create();
		center(n);
		n.setColor(Color.white);
		translateTo(n, emptyCell);
		n.drawRect(0, 0, emptyCell.getWidth(), emptyCell.getHeight());
		n.dispose();
	}

	@Override
	public void render(Player player) {
		Graphics2D n = (Graphics2D) g.create();
		center(n);
		n.setColor(Color.blue);
		n.fillRect(player.getX() * GrowCell.WIDTH, player.getY() * GrowCell.HEIGHT, GrowCell.WIDTH + 1,
				GrowCell.HEIGHT + 1);
		n.dispose();
	}
	

	@Override
	public void render(Necromonger monger) {
		Graphics2D n = (Graphics2D) g.create();
		center(n);
		n.translate(monger.getX(), monger.getY());
		n.setColor(Color.red);
		n.fillOval(0, 0, GrowCell.WIDTH, GrowCell.HEIGHT);
		n.dispose();
	}

	private void drawPlayerGoals(Player player) {
		hudOverlay.setColor(Color.blue);
		hudOverlay.drawString("Current Goal: " + player.getGoal().toString(), 10,
				(int) (frame.getHeight() * .2d) - (hudOverlay.getFontMetrics().getHeight() + 2));
	}

	@Override
	public void setGraphics(Graphics2D g2d) {
		this.g = g2d;
	}
	
	private void translateToCentered(Graphics2D n, GrowCell cell) {
		n.translate(cell.getX() * GrowCell.WIDTH, cell.getY() * GrowCell.HEIGHT);
		n.translate(GrowCell.WIDTH / 2d, GrowCell.HEIGHT / 2d);
	}

	private void center(Graphics2D n) {
		n.translate(frame.getWidth() / 2d, frame.getHeight() / 2d);
		translateToPlayer(n, game.getCurrentPlayer());
	}

	private void translateTo(Graphics2D n, GrowCell growCell) {
		n.translate(growCell.getX() * GrowCell.WIDTH, growCell.getY() * GrowCell.HEIGHT);
	}

	private void translateToPlayer(Graphics2D n, Player player) {
		n.translate(-player.getX() * GrowCell.HEIGHT, -player.getY() * GrowCell.WIDTH);
	}

	@Override
	public void render(CellContainer cellContainer) {
		for(GrowCell cell : cellContainer.getCells()) {
			cell.render(this);
		}
	}

	@Override
	public void render(LifeCell lifeCell) {
		Graphics2D n = (Graphics2D) g.create();
		center(n);
		n.setColor(Color.gray.darker());
		translateTo(n, lifeCell);
		n.fillRect(0, 0, lifeCell.getWidth(), lifeCell.getHeight());
		n.dispose();
	}

	@Override
	public void render(SpawnerCell spawnerCell) {
		Graphics2D n = (Graphics2D) g.create();
		center(n);
		n.setColor(Color.cyan);
		translateTo(n, spawnerCell);
		n.fillRect(0, 0, 10, 10);
		n.dispose();
	}

}
