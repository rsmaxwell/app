package com.rsmaxwell.infection.app;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;
import com.rsmaxwell.infection.model.config.Pair;

public class AppConfig {

	public double iStart;
	public double maxTime;
	public double transmission;
	public int resolution;
	public String integrationMethod;

	public transient double sStart;
	public transient double rStart;
	public transient Map<String, Group> groups = new HashMap<String, Group>();
	public transient Map<Pair, Connector> connectors = new HashMap<Pair, Connector>();

	public static AppConfig load(String dirname) throws Exception {
		AppConfig appConfig = null;
		Gson gson = new Gson();

		// ******************************************************************
		// * Read the overall configuration
		// ******************************************************************
		String filename = Paths.get(dirname, "config.json").toString();
		try {
			appConfig = gson.fromJson(new FileReader(filename), AppConfig.class);
		} catch (Exception e) {
			throw new Exception(filename, e);
		}

		// ******************************************************************
		// * Read the population groups
		// ******************************************************************
		String groupDir = Paths.get(dirname, "groups").toString();
		File dir = new File(groupDir);
		File[] directoryListing = dir.listFiles();
		if (directoryListing == null) {
			throw new Exception("There are no groups");
		}

		try {
			for (File child : directoryListing) {
				String key = getGroupName(child);
				filename = Paths.get(child.getCanonicalPath(), "config.json").toString();
				Group group = gson.fromJson(new FileReader(filename), Group.class);
				appConfig.groups.put(key, group);
			}
		} catch (Exception e) {
			throw new Exception(filename, e);
		}

		// ******************************************************************
		// * Read the group connectors
		// ******************************************************************
		String connectorDir = Paths.get(dirname, "connectors").toString();
		dir = new File(connectorDir);
		directoryListing = dir.listFiles();
		if (directoryListing == null) {
			throw new Exception("There are no connectors");
		}

		try {
			for (File child : directoryListing) {
				Pair key = getConnectorName(child);
				filename = Paths.get(child.getCanonicalPath(), "config.json").toString();
				Connector connector = gson.fromJson(new FileReader(filename), Connector.class);
				appConfig.connectors.put(key, connector);
			}
		} catch (Exception e) {
			throw new Exception(filename, e);
		}

		return appConfig;
	}

	private static String getGroupName(File file) throws Exception {
		String name = file.getName();
		return Group.validateName(name);
	}

	private static Pair getConnectorName(File file) throws Exception {
		String name = file.getName();
		return Connector.validateName(name);
	}

	public Config toConfig() throws Exception {

		Config config = new Config();
		config.iStart = iStart;
		config.maxTime = maxTime;
		config.transmission = transmission;
		config.resolution = resolution;
		config.integrationMethod = integrationMethod;
		config.sStart = sStart;
		config.rStart = rStart;
		config.groups = new HashMap<String, Group>(groups);
		config.connectors = new HashMap<Pair, Connector>(connectors);

		return config.init();
	}
}
