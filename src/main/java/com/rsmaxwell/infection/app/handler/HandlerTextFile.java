package com.rsmaxwell.infection.app.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.rsmaxwell.infection.model.config.Config;

public class HandlerTextFile extends HandlerFile {

	@Override
	public void check(Config config) throws Exception {

		boolean isText = config.handler.isText();
		if (isText == false) {
			throw new Exception("Inconsistant configuration: " + this.getClass().getSimpleName() + ", " + config.handler.getClass().getSimpleName());
		}
	}

	@Override
	public void display(byte[] array, Config config, Map<String, Object> parameters) throws IOException {

		File file = getFile(config, parameters);
		String strContent = new String(array, StandardCharsets.UTF_8);

		try (FileOutputStream fos = new FileOutputStream(file)) {
			try (PrintWriter printWriter = new PrintWriter(fos)) {
				printWriter.print(strContent);
			}
		}
	}
}
