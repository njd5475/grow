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
		g.drawString(String.format("%.02f", growGame.getGameClock()), 10, 2 + g.getFontMetrics().getHeight());
	}

	@Override
	public void setGraphics(Graphics2D g2d) {
		this.g = g2d;
	}

	@Override
	public void render(GrowingCell cell) {
		Graphics2D n = (Graphics2D) g.create();
		n.translate(frame.getWidth() / 2, frame.getHeight() / 2);
		n.setColor(Color.white);
		n.fillRect(cell.getX() * GrowCell.WIDTH, cell.getY() * GrowCell.HEIGHT, cell.getWidth(), cell.getHeight());
		n.dispose();
	}

}
