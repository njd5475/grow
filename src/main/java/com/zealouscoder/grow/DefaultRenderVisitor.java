package com.zealouscoder.grow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import com.zealouscoder.grow.cells.EmptyCell;
import com.zealouscoder.grow.cells.GrowCell;
import com.zealouscoder.grow.cells.GrowingCell;

public class DefaultRenderVisitor implements RenderVisitor {

	private JFrame frame;
	private Font derivedFont;

	private transient Graphics2D g;
	private transient Graphics2D hudOverlay;
	private transient GrowGame game;

	public DefaultRenderVisitor(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void render(GrowGame game) {
		hudOverlay = (Graphics2D) g.create();
		this.game = game;
		game.getGrid().render(this);
		render(game.getCurrentPlayer());
		drawGameClock(game);
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
}
