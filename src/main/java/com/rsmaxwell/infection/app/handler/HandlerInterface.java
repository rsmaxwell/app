package com.rsmaxwell.infection.app.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.rsmaxwell.infection.model.config.Config;

public interface HandlerInterface {

	void check(Config config) throws Exception;

	boolean requiresOutputDirectory();

	void display(byte[] array, Config config, Map<String, Object> parameters) throws FileNotFoundException, IOException;
}
