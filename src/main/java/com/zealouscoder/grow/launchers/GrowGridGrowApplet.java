package com.zealouscoder.grow.launchers;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JApplet;

import com.zealouscoder.grow.DefaultRenderVisitor;
import com.zealouscoder.grow.DefaultUpdateVisitor;
import com.zealouscoder.grow.GrowGame;
import com.zealouscoder.grow.RenderVisitor;
import com.zealouscoder.grow.UpdateVisitor;

public class GrowGridGrowApplet extends JApplet {

	public GrowGridGrowApplet() throws HeadlessException {
		final GrowGame game = new GrowGame();
		RenderVisitor renderer = new DefaultRenderVisitor(this.getContentPane());
		this.setLayout(new BorderLayout());
		this.add(new GrowGridGrow(game, renderer));
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// revert to old event
				game.queueButtonEvent(KeyEvent.KEY_PRESSED, e.getKeyCode());
				super.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// revert to old event
				game.queueButtonEvent(KeyEvent.KEY_RELEASED, e.getKeyCode());
				super.keyReleased(e);
			}
		});
		final UpdateVisitor updater = new DefaultUpdateVisitor(game);
		(new Thread() {
			public void run() {
				Object lock = new Object();
				synchronized (lock) {
					while (game.isRunning()) {
						GrowGridGrowApplet.this.repaint();

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
