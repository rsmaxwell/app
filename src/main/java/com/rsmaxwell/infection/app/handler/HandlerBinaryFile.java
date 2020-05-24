package com.rsmaxwell.infection.app.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.rsmaxwell.infection.model.config.Config;

public class HandlerBinaryFile extends HandlerFile {

	@Override
	public void check(Config config) throws Exception {

		boolean isText = config.handler.isText();
		if (isText == true) {
			throw new Exception("Inconsistant configuration: " + this.getClass().getSimpleName() + ", " + config.handler.getClass().getSimpleName());
		}
	}

	@Override
	public void display(byte[] array, Config config, Map<String, Object> parameters) throws IOException {

		File file = getFile(config, parameters);

		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(array);
		}
	}
}
