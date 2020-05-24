package com.rsmaxwell.infection.app.handler;

import java.io.File;
import java.util.Map;

import com.rsmaxwell.infection.model.config.Config;

public abstract class HandlerFile extends Handler {

	@Override
	public boolean requiresOutputDirectory() {
		return true;
	}

	public File getFile(Config config, Map<String, Object> parameters) {
		String filename = config.handler.getFilename();
		File outputDirectory = (File) parameters.get("outputDirectory");
		return new File(outputDirectory, filename);
	}
}
