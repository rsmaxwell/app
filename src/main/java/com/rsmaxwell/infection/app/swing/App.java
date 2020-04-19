
package com.rsmaxwell.infection.app.swing;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.rsmaxwell.infection.app.AppConfig;
import com.rsmaxwell.infection.model.app.Version;
import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.model.Model;
import com.rsmaxwell.infection.model.model.Populations;

public class App {

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
					            .desc("configuration directory")
					            .build();
			
			Option filter = Option.builder("f")
					            .longOpt("filter")
					            .argName("filter")
					            .hasArg()
					            .desc("filter population results")
					            .build();
			// @formatter:on

		Options options = new Options();
		options.addOption(version);
		options.addOption(help);
		options.addOption(config);
		options.addOption(filter);

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

	public static void main(String[] args) throws Exception {

		CommandLine line = getCommandLine(args);

		String dirname = "config";
		if (line.hasOption('c')) {
			dirname = line.getOptionValue("c");
		}
		Config config = AppConfig.load(dirname).toConfig();

		String[] filter = line.getOptionValues("f");

		Model model = new Model(config);
		Populations populations = model.run();

		populations.swing(filter);
	}
}