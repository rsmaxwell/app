package com.rsmaxwell.infection.app.handler;

public abstract class Handler implements HandlerInterface {

	@Override
	public boolean requiresOutputDirectory() {
		return false;
	}
}
