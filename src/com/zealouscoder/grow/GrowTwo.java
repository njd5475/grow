package com.zealouscoder.grow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GrowTwo extends JPanel {

	private RenderVisitor renderer;
	private GrowGame game;

	public GrowTwo(GrowGame game, RenderVisitor renderer) {
		this.renderer = renderer;
		this.game = game;
		setPreferredSize(new Dimension(1024, 768));
		setDoubleBuffered(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		renderer.setGraphics(g2d);
		renderer.render(game);
		g2d.dispose();
	}

	public static void main(String[] args) {
		final GrowGame game = new GrowGame();
		JFrame frame = new JFrame("Grid Grow");
		RenderVisitor renderer = new DefaultRenderVisitor(frame);
		frame.setLayout(new BorderLayout());
		frame.add(new GrowTwo(game, renderer));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		UpdateVisitor updater = new DefaultUpdateVisitor(game);
		(new Thread() {
			public void run() {
				Object lock = new Object();
				synchronized (lock) {
					while (game.isRunning()) {
						frame.repaint();

						try {
							lock.wait(33);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		(new Thread() {
			private static final double fixedtimestep = 0.01;
			private long lasttime = -1;
			private double accumulator = 0;

			public void run() {
				Object lock = new Object();

				synchronized (lock) {
					while (game.isRunning()) {
						long currenttime = System.currentTimeMillis(); //only get the time once per loop
						if (lasttime == -1) {
							lasttime = currenttime;
						}
						double dt = (currenttime - lasttime) / 1000d;
						lasttime = currenttime;

						accumulator += dt; // accumulate

						while (accumulator > fixedtimestep) {
							game.update(fixedtimestep, updater);

							// drain accumulator
							accumulator -= fixedtimestep;
						}

						try {
							lock.wait(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

}
