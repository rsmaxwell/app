
package com.rsmaxwell.infection.app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.rsmaxwell.infection.app.handler.Handler;
import com.rsmaxwell.infection.app.handler.HandlerBinaryFile;
import com.rsmaxwell.infection.app.handler.HandlerStdout;
import com.rsmaxwell.infection.app.handler.HandlerSwingImage;
import com.rsmaxwell.infection.app.handler.HandlerSwingSVG;
import com.rsmaxwell.infection.app.handler.HandlerTextFile;
import com.rsmaxwell.infection.app.handler.ResponseCollector;
import com.rsmaxwell.infection.model.app.MySecurityManager;
import com.rsmaxwell.infection.model.app.Version;
import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.engine.Engine;;

public class App {

	private static Map<String, Handler> handlers = new HashMap<String, Handler>();

	static {
		handlers.put("textfile", new HandlerTextFile());
		handlers.put("binaryfile", new HandlerBinaryFile());
		handlers.put("stdout", new HandlerStdout());
		handlers.put("swingimage", new HandlerSwingImage());
		handlers.put("swingsvg", new HandlerSwingSVG());
	}

	private static CommandLine getCommandLine(String[] args) throws ParseException {

		// @formatter:off
			Option version = Option.builder("v")
					            .longOpt("version")
					            .argName("version")
					            .desc("show program version")
					            .build();
			
			Option help = Option.builder("h")
					            .longOpt("help")
					            .argName("help")
					            .desc("show program help")
					            .build();
			
			Option config = Option.builder("c")
					            .longOpt("config")
					            .argName("config")
					            .hasArg()
					            .desc("configuration file (*.json)")
					            .build();
			
			Option outputMethod = Option.builder("o")
					            .longOpt("outputmethod")
					            .argName("output method")
					            .hasArg()
					            .required()
					            .desc("output method")
					            .build();
			
			Option outputDirectory = Option.builder("d")
					            .longOpt("output")
					            .argName("output directory")
					            .hasArg()
					            .desc("Name of the output directory")
					            .build();
			// @formatter:on

		Options options = new Options();
		options.addOption(version);
		options.addOption(help);
		options.addOption(config);
		options.addOption(outputMethod);
		options.addOption(outputDirectory);

		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(options, args);

		if (line.hasOption("version")) {
			System.out.println("version:   " + Version.version());
			System.out.println("buildDate: " + Version.buildDate());
			System.out.println("gitCommit: " + Version.gitCommit());
			System.out.println("gitBranch: " + Version.gitBranch());
			System.out.println("gitURL:    " + Version.gitURL());

		} else if (line.hasOption('h')) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("infection <OPTION> ", options);
		}

		return line;
	}

	private static void test(String regex, String string) {

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string);
		boolean b = m.matches();

		System.out.println("b = " + b);
	}

	public static void main(String[] args) throws Exception {

		try {
			System.setSecurityManager(new MySecurityManager());
		} catch (SecurityException se) {
			System.out.println("SecurityManager already set!");
		}

		CommandLine line = getCommandLine(args);

		String configFile = line.getOptionValue("c", "config");
		Config config = Config.loadFromFile(configFile);

		String outputMethod = line.getOptionValue("o");
		Handler handler = handlers.get(outputMethod);
		if (handler == null) {
			throw new Exception("Unexpected output method: '" + outputMethod + "'");
		}
		handler.check(config);

		Map<String, Object> parameters = new HashMap<String, Object>();
		boolean requiresOutputDirectory = handler.requiresOutputDirectory();
		if (requiresOutputDirectory) {
			String outputDirectoryName = line.getOptionValue("d", "output");
			File outputDirectory = new File(outputDirectoryName);
			if (!outputDirectory.isDirectory()) {
				throw new Exception("'" + outputDirectoryName + "' is not a Directory");
			}
			parameters.put("outputDirectory", outputDirectory);
		}

		Engine engine = new Engine(config);
		ResponseCollector response = new ResponseCollector();
		engine.run(response);
		byte[] array = response.getArray();

		handler.display(array, config, parameters);
	}
}
