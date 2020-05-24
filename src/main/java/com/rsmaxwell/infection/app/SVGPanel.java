package com.rsmaxwell.infection.app;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SVGPanel extends JPanel {

	private BufferedImage image;

	public SVGPanel(byte[] imageData, int width, int height) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
		image = ImageIO.read(bais);

		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}
