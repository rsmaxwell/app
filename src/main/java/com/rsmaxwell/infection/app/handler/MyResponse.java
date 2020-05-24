package com.rsmaxwell.infection.app.handler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.rsmaxwell.infection.model.handler.MyResponseInterface;

public class MyResponse implements MyResponseInterface {

	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public PrintWriter getWriter() {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
		return new PrintWriter(outputStreamWriter);
	}

	public byte[] getArray() {
		return outputStream.toByteArray();
	}
}
