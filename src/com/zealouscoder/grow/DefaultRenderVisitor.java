package com.zealouscoder.grow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class DefaultRenderVisitor implements RenderVisitor {

	private JFrame frame;
	private Graphics2D g;
	private Font derivedFont;
	private int centerX;
	private int centerY;

	public DefaultRenderVisitor(JFrame frame) {
		this.frame = frame;
		this.centerX = frame.getWidth() / 2;
		this.centerY = frame.getHeight() / 2;
	}

	@Override
	public void render(GrowGrid growGrid) {
		for (GrowCell cell : growGrid.getCells()) {
			cell.render(this);
		}
	}

	@Override
	public void render(GrowGame growGame) {
		growGame.getGrid().render(this);
		drawGameClock(growGame);
	}

	private void drawGameClock(GrowGame growGame) {
		g.setColor(Color.blue);
		if (derivedFont == null) {
			derivedFont = g.getFont().deriveFont(Font.BOLD, 24);
		}
		g.setFont(derivedFont);
		int currentLine = 0;
		g.drawString(String.format("Game Time: %.02f", growGame.getGameClock()), 10, (++currentLine) * (2 + g.getFontMetrics().getHeight()));
		g.drawString(String.format("Growth Rate: %.02f", growGame.getGrowthRate()), 10, (++currentLine) * (2 + g.getFontMetrics().getHeight()));
	}

	@Override
	public void setGraphics(Graphics2D g2d) {
		this.g = g2d;
	}

	@Override
	public void render(GrowingCell cell) {
		Graphics2D n = (Graphics2D) g.create();
		center(n);
		n.setColor(Color.white);
		translateToCentered(n, cell);
		n.fillRect(-cell.getWidth()/2, -cell.getHeight()/2, cell.getWidth(), cell.getHeight());
		n.dispose();
	}

	private void translateToCentered(Graphics2D n, GrowCell cell) {
		n.translate(cell.getX() * GrowCell.WIDTH, cell.getY() * GrowCell.HEIGHT);
		n.translate(GrowCell.WIDTH/2, GrowCell.HEIGHT/2);
	}

	private void center(Graphics2D n) {
		n.translate(frame.getWidth() / 2, frame.getHeight() / 2);
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

	private void translateTo(Graphics2D n, GrowCell growCell) {
		n.translate(growCell.getX() * GrowCell.WIDTH, growCell.getY() * GrowCell.HEIGHT);
	}

}
