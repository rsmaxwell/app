package app;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.config.ConfigSyntaxException;

public class ConfigTest {

	public static void main(String[] args) throws Exception {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.println("{");
		pw.println("    \"maxTime\": 20,");
		pw.println("    \"integrationMethod\": \"com.rsmaxwell.infection.model.integrate.RungeKutta\",");
		pw.println("    \"groups\": {");
		pw.println("        \"1\": {");
		pw.println("            \"name\": \"everyone\",");
		pw.println("            \"recovery\": 0.23,");
		pw.println("            \"population\": 10,");
		pw.println("            \"iStart\": 0.01");
		pw.println("        }");
		pw.println("    },");
		pw.println("    \"connectors\": {");
		pw.println("        \"1.1\": {");
		pw.println("            \"transmission\": 2.3");
		pw.println("       }");
		pw.println("    },");
		pw.println("    \"response\": {");
		pw.println("        \"type\": \"png\",");
		pw.println("        \"width\": 800,");
		pw.println("        \"height\": 400");
		pw.println("    }");
		pw.println("}");

		String json = sw.getBuffer().toString();

		Config config = null;
		try {
			config = Config.load(json);
		} catch (ConfigSyntaxException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

		System.out.println("config: " + config.toString());
	}
}
