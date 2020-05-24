package com.rsmaxwell.infection.app.handler;

import java.util.Map;

import com.rsmaxwell.infection.model.config.Config;

public class HandlerStdout extends Handler {

	@Override
	public void check(Config config) throws Exception {

		boolean isText = config.handler.isText();
		if (isText == false) {
			throw new Exception("Inconsistant configuration: " + this.getClass().getSimpleName() + ", " + config.handler.getClass().getSimpleName());
		}
	}

	@Override
	public void display(byte[] array, Config config, Map<String, Object> parameters) {
		String string = new String(array);
		System.out.println(string);
	}
}
