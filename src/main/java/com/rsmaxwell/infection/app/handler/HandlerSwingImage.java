package com.rsmaxwell.infection.app.handler;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import com.rsmaxwell.infection.app.ImagePanel;
import com.rsmaxwell.infection.model.config.Config;

public class HandlerSwingImage extends Handler {

	@Override
	public void check(Config config) throws Exception {
		boolean isImage = config.handler.isImage();
		if (isImage == false) {
			throw new Exception("Inconsistant configuration: " + this.getClass().getSimpleName() + ", " + config.handler.getClass().getSimpleName());
		}
	}

	@Override
	public void display(byte[] bytearray, Config config, Map<String, Object> parameters) throws IOException {

		int width = 0;
		int height = 0;

		Object object = config.output.get("width");
		if (object instanceof Number) {
			Number number = (Number) object;
			width = number.intValue();
		}

		object = config.output.get("height");
		if (object instanceof Number) {
			Number number = (Number) object;
			height = number.intValue();
		}

		// ******************************************
		// * Add the image panel to the Swing JFrame
		// ******************************************
		ImagePanel chartPanel = new ImagePanel(bytearray, width, height);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);

		JFrame frame = new JFrame("Infection Simulation");
		frame.add(chartPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
