package com.zealouscoder.grow;

import java.awt.Graphics2D;

public interface RenderVisitor {

	public void render(GrowGrid growGrid);

	public void render(GrowGame growGame);

	public void setGraphics(Graphics2D g2d);

	public void render(GrowingCell growingCell);

	public void render(EmptyCell emptyCell);
	
}
